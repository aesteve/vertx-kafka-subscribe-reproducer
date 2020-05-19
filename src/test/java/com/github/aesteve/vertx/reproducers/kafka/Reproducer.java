package com.github.aesteve.vertx.reproducers.kafka;

import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.kafka.client.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(VertxExtension.class)
class Reproducer {

    private KafkaConsumer<String, String> consumer;

    @BeforeEach
    void createKafkaConsumer(Vertx vertx) {
        consumer = createKafkaClient(vertx);
    }

    @AfterEach
    void closeCKafkaConsumer(Vertx vertx) {
        consumer.close();
        consumer = null;
    }


    @Test
    /**
     * This test fails:
     * subscribing to the topic returns a proper completed future, even though no broker runs on port 1234
     */
    void subscribeToNonExistingKafkaClusterShouldFailProperly(VertxTestContext ctx) {
        consumer.handler(System.out::println)
                .subscribe("something", ctx.failing(e -> ctx.completeNow()));
    }

    @Test
    // This test works as expected
    void assignTopicThatDoesntExistShouldFailProperly(VertxTestContext ctx) {
        consumer.partitionsFor("something", ctx.failing(e -> ctx.completeNow()));
    }


    private KafkaConsumer<String, String> createKafkaClient(Vertx vertx) {
        // the exact example from the docs, changed port to make sure Kafka is not running on the machine running the tests
        Map<String, String> config = new HashMap<>();
        config.put("bootstrap.servers", "localhost:1234");
        config.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        config.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        config.put("group.id", "my_group");
        config.put("auto.offset.reset", "earliest");
        config.put("enable.auto.commit", "false");
        // Addition to the docs, so that test fail earlier
        config.put(ConsumerConfig.DEFAULT_API_TIMEOUT_MS_CONFIG, "1000");
        return KafkaConsumer.create(vertx, config);
    }

}
