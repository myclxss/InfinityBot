plugins {
    id("java")
    id("application")
    id("com.gradleup.shadow") version "9.0.0-beta13"
}

group = "dev.anhuar"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io") 
}

dependencies {
    //BASICS
    implementation("net.dv8tion:JDA:6.1.1")
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("ch.qos.logback:logback-classic:1.5.13")

    //ANOTATIONS
    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")

    //DB MANAGER
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.mongodb:mongodb-driver-sync:5.6.0")

    //MANAGE FILES
    implementation("org.yaml:snakeyaml:2.0")
}

application {
    mainClass.set("dev.anhuar.InfinityBot")
}

tasks.test {
    useJUnitPlatform()
}

tasks.shadowJar {
    manifest {
        attributes["Main-Class"] = "dev.anhuar.InfinityBot"
    }
    archiveClassifier.set("dev")
    archiveBaseName.set(project.name)
}