plugins {
    id("java")
}

group = "ru.siobko.testing"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("io.rest-assured:rest-assured:5.4.0")

    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.17.1")

    testImplementation("io.goodforgod:slf4j-simple-logger:2.0.0")

    testCompileOnly ("org.projectlombok:lombok:1.18.32")
    testAnnotationProcessor ("org.projectlombok:lombok:1.18.32")

    testImplementation("org.assertj:assertj-core:3.26.0")

    testImplementation("io.rest-assured:json-schema-validator:5.4.0")
}

tasks.test {
    useJUnitPlatform()
}