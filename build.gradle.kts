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
}

tasks.test {
    useJUnitPlatform()
}