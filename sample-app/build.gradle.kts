plugins {
    kotlin("jvm") version "1.9.22"
}

group = "org.ptudy.spring-kotlin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("mysql:mysql-connector-java:8.0.33")
<<<<<<< Updated upstream
    implementation(project(":jinia91-spring-core"))
=======
    implementation(project(":jinia91-spring-context"))
    // jakarta validation
    implementation("org.hibernate.validator:hibernate-validator:7.0.1.Final")
    implementation("org.glassfish:jakarta.el:3.0.3")
    implementation("ch.qos.logback:logback-classic:1.4.14")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

>>>>>>> Stashed changes
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}