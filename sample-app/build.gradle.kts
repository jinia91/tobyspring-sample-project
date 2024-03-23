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
    implementation(project(":jinia91-spring-context"))
    // jakarta validation
    implementation("org.hibernate.validator:hibernate-validator:7.0.1.Final")
    implementation("org.glassfish:jakarta.el:3.0.3")
    implementation("ch.qos.logback:logback-classic:1.4.14")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("io.kotest:kotest-assertions-core:5.8.0")
    testImplementation("com.navercorp.fixturemonkey:fixture-monkey-starter-kotlin:1.0.14")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}