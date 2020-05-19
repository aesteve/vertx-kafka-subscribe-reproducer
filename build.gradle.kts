plugins {
  java
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("io.vertx:vertx-kafka-client:3.9.0")
    testImplementation("io.vertx:vertx-junit5:3.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
}

tasks {
    withType<Wrapper> {
        gradleVersion = "6.4"
    }
    withType<JavaCompile> {
        sourceCompatibility = JavaVersion.VERSION_11.toString()
        targetCompatibility = JavaVersion.VERSION_11.toString()
    }
    test {
        useJUnitPlatform()
    }

}
