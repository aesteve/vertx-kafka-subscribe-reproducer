plugins {
  java
}

repositories {
    mavenCentral()
}

val vertxVersion = "4.4.1"

dependencies {
    testImplementation("io.vertx:vertx-kafka-client:$vertxVersion")
    testImplementation("io.vertx:vertx-junit5:$vertxVersion")
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
