plugins {
    kotlin("jvm") version "1.8.0"
    `maven-publish`
}

group = "fan.yumetsuki"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

publishing {
    publications {
        create<MavenPublication>("KekkonSystem") {
            groupId = "fan.yumetsuki"
            artifactId = "kekkon-system"

            from(components["java"])
        }
    }

    repositories {
        mavenLocal()
    }
}