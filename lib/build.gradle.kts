version = "0.0.1"

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

fun String.join(vararg postfix: String): String {
    check(isNotEmpty())
    return postfix.filter { it.isNotEmpty() }.joinToString(separator = "", prefix = this) {
        it.capitalize()
    }
}

@Deprecated(
    level = DeprecationLevel.ERROR,
    message = "Use Project.version property.",
    replaceWith = ReplaceWith("version.toString()"),
)
fun Project.version(): String {
    error("Use Project.version property.")
}

fun Project.version(vararg postfix: String): String {
    val prefix = version.toString()
    check(prefix.isNotEmpty())
    return postfix.filter { it.isNotEmpty() }.joinToString(separator = "-", prefix = "$prefix-")
}

tasks.getByName<JavaCompile>("compileJava") {
    targetCompatibility = Version.jvmTarget
}

val compileKotlinTask = tasks.getByName<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>("compileKotlin") {
    kotlinOptions {
        jvmTarget = Version.jvmTarget
        freeCompilerArgs = freeCompilerArgs + setOf("-module-name", Maven.groupId + ":" + Maven.artifactId)
    }
}

tasks.getByName<JavaCompile>("compileTestJava") {
    targetCompatibility = Version.jvmTarget
}

tasks.getByName<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>("compileTestKotlin") {
    kotlinOptions.jvmTarget = Version.jvmTarget
}

"snapshot".also { variant ->
    task<Jar>("assemble".join(variant, "Jar")) {
        dependsOn(compileKotlinTask)
        archiveBaseName.set(Maven.artifactId)
        archiveVersion.set(project.version(variant.toUpperCase()))
        from(compileKotlinTask.destinationDirectory.asFileTree)
    }
}
