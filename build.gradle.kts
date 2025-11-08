plugins {
    id("java")
}

group = "dev.anhuar"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.dv8tion:JDA:6.1.0")

    //ANOTATIONS
    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")

    //DB MANAGER
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.mongodb:mongodb-driver-sync:5.2.1")

    //MANAGE FILES
    implementation("org.yaml:snakeyaml:2.0")
}

tasks.test {
    useJUnitPlatform()
}