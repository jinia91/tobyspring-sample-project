plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "spring_study"

include(
    "jinia91-spring-context",
    "sample-app"
)