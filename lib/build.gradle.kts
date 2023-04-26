repositories.mavenCentral()

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.gradle.jacoco") // todo
    id("io.gitlab.arturbosch.detekt") version Version.detekt // todo
    id("org.jetbrains.dokka") version Version.dokka // todo
}

jacoco {
    toolVersion = Version.jacoco
}
