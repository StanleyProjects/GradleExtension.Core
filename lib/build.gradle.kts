import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

version = "0.0.2"

repositories {
    mavenCentral()
//    maven("https://repo.gradle.org/gradle/libs-releases-local")
}

plugins {
    id("org.jetbrains.kotlin.jvm")
//    id("org.gradle.java-gradle-plugin")
//    id("org.gradle.java-test-fixtures")
    id("org.gradle.jacoco")
    id("io.gitlab.arturbosch.detekt") version Version.detekt // todo
    id("org.jetbrains.dokka") version Version.dokka // todo
}

dependencies {
//    implementation("org.gradle:gradle-core-api:6.1.1")
//    implementation("org.gradle:gradle-core-api:7.6.1")
//    implementation("org.gradle:gradle-api:7.6.1")
    implementation(gradleApi())
    val version = "7.6.1"
    val tree = fileTree("/opt/gradle-${version}/lib") {
        setOf(
            "api-metadata",
            "base-annotations",
            "base-services",
            "base-services-groovy",
            "bootstrap",
//            "build-cache",
//            "build-cache-base",
//            "build-cache-packaging",
//            "build-events",
//            "build-operations",
//            "build-option",
            "cli",
            "core",
            "core-api",
            "enterprise-logging",
            "enterprise-operations",
            "enterprise-workers",
            "execution",
            "file-collections",
            "file-temp",
            "file-watching",
            "files",
            "functional",
            "hashing",
            "installation-beacon",
            "jvm-services",
            "kotlin-dsl",
            "kotlin-dsl-tooling-models",
            "launcher",
            "logging",
            "logging-api",
            "messaging",
            "model-core",
            "model-groovy",
            "native",
            "normalization-java",
            "persistent-cache",
            "problems",
            "process-services",
            "resources",
            "runtime-api-info",
            "snapshots",
            "tooling-api",
            "worker-processes",
            "worker-services",
            "wrapper-shared",
        ).forEach { name ->
            include("gradle-${name}-${version}.jar")
        }
    }
//    println(tree.joinToString("\n") { it.absolutePath })
//    implementation(tree)
//    testImplementation("org.gradle:gradle-test-kit:6.1.1")
//    testImplementation(testFixtures(project))
//    testImplementation("org.apache.groovy:groovy:4.0.11")
//    testImplementation("org.slf4j:slf4j-api:2.0.7")
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

val taskUnitTest = tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

val taskCoverageReport = task<JacocoReport>("assembleCoverageReport") {
    dependsOn(taskUnitTest)
    reports {
        csv.required.set(false)
        html.required.set(true)
        xml.required.set(false)
    }
//    sourceDirectories.setFrom(file("src/main/kotlin"))
//    val dirs = fileTree(buildDir("tmp/kotlin-classes/${variant.name}"))
//    classDirectories.setFrom(dirs)
//    executionData(buildDir("outputs/unit_test_code_coverage/${variant.name}UnitTest/${taskUnitTest.name}.exec"))
}

"snapshot".also { variant ->
    val version = project.version(variant.toUpperCase())
    task<Jar>("assemble".join(variant, "Jar")) {
        dependsOn(compileKotlinTask)
        archiveBaseName.set(Maven.artifactId)
        archiveVersion.set(version)
        from(compileKotlinTask.destinationDirectory.asFileTree)
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
