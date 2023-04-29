import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

version = "0.0.4"

repositories {
    mavenCentral()
}

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.gradle.jacoco")
    id("io.gitlab.arturbosch.detekt") version Version.detekt // todo
    id("org.jetbrains.dokka") version Version.dokka // todo
}

dependencies {
    implementation(gradleApi())
    testImplementation("org.junit.jupiter:junit-jupiter-api:${Version.jupiter}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${Version.jupiter}")
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
    return postfix.filter { it.isNotEmpty() }
        .also { check(it.isNotEmpty()) }
        .joinToString(separator = "-", prefix = "$prefix-")
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

val taskUnitTest = task<Test>("checkUnitTest") {
    useJUnitPlatform()
    testClassesDirs = sourceSets.test.get().output.classesDirs
    classpath = sourceSets.test.get().runtimeClasspath
}

val taskCoverageReport = task<JacocoReport>("assembleCoverageReport") {
    dependsOn(taskUnitTest)
    reports {
        csv.required.set(false)
        html.required.set(true)
        xml.required.set(false)
    }
    sourceDirectories.setFrom(sourceSets.main.get().allSource)
    classDirectories.setFrom(sourceSets.main.get().output.classesDirs)
    executionData(taskUnitTest)
}

task<JacocoCoverageVerification>("checkCoverage") {
    dependsOn(taskCoverageReport)
    violationRules {
        rule {
            limit {
                minimum = BigDecimal(0.96)
            }
        }
    }
    classDirectories.setFrom(taskCoverageReport.classDirectories)
    executionData(taskCoverageReport.executionData)
}

"snapshot".also { variant ->
    val version = project.version(variant.toUpperCase())
    task<Jar>("assemble".join(variant, "Jar")) {
        dependsOn(compileKotlinTask)
        archiveBaseName.set(Maven.artifactId)
        archiveVersion.set(version)
        from(compileKotlinTask.destinationDirectory.asFileTree)
    }
    task<Jar>("assemble".join(variant, "Source")) {
        archiveBaseName.set(Maven.artifactId)
        archiveVersion.set(version)
        archiveClassifier.set("sources")
        from(sourceSets.main.get().allSource)
    }
    task("assemble".join(variant, "Pom")) {
        doLast {
            val target = buildDir.resolve("libs/${Maven.artifactId}-$version.pom")
            if (target.exists()) {
                target.delete()
            } else {
                target.parentFile?.mkdirs()
            }
            val text = MavenUtil.pom(
                groupId = Maven.groupId,
                artifactId = Maven.artifactId,
                version = version,
                packaging = "jar"
            )
            target.writeText(text)
        }
    }
    task("assemble".join(variant, "MavenMetadata")) {
        doLast {
            val target = buildDir.resolve("xml/maven-metadata.xml")
            if (target.exists()) {
                target.delete()
            } else {
                target.parentFile?.mkdirs()
            }
            val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
            val text = """
                <metadata>
                    <groupId>${Maven.groupId}</groupId>
                    <artifactId>${Maven.artifactId}</artifactId>
                    <versioning>
                        <versions>
                            <version>$version</version>
                        </versions>
                        <lastUpdated>${formatter.format(LocalDateTime.now())}</lastUpdated>
                    </versioning>
                </metadata>
            """.trimIndent()
            target.writeText(text)
        }
    }
}
