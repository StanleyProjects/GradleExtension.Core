import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import sp.gx.core.Badge
import sp.gx.core.GitHub
import sp.gx.core.Markdown
import sp.gx.core.Maven
import sp.gx.core.assemble
import sp.gx.core.camelCase
import sp.gx.core.check
import sp.gx.core.colonCase
import sp.gx.core.existing
import sp.gx.core.file
import sp.gx.core.filled
import sp.gx.core.kebabCase
import sp.gx.core.resolve
import java.net.URL
import java.util.Locale

version = "0.5.0"

val maven = Maven.Artifact(
    group = "com.github.kepocnhh",
    id = rootProject.name,
)

val gh = GitHub.Repository(
    owner = "StanleyProjects",
    name = rootProject.name,
)

repositories.mavenCentral()

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.gradle.jacoco")
    id("io.gitlab.arturbosch.detekt") version Version.detekt
    id("org.jetbrains.dokka") version Version.dokka
}

dependencies {
    implementation(gradleApi())
    testImplementation("org.junit.jupiter:junit-jupiter-api:${Version.jupiter}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${Version.jupiter}")
}

jacoco.toolVersion = Version.jacoco

tasks.getByName<JavaCompile>("compileJava") {
    targetCompatibility = Version.JVM_TARGET
}

val compileKotlinTask = tasks.getByName<KotlinCompile>("compileKotlin") {
    kotlinOptions {
        jvmTarget = Version.JVM_TARGET
        freeCompilerArgs = freeCompilerArgs + setOf("-module-name", colonCase(maven.group, maven.id))
    }
}

tasks.getByName<JavaCompile>("compileTestJava") {
    targetCompatibility = Version.JVM_TARGET
}

tasks.getByName<KotlinCompile>("compileTestKotlin") {
    kotlinOptions.jvmTarget = Version.JVM_TARGET
}

fun Test.getExecutionData(): File {
    return layout.buildDirectory.get()
        .dir("jacoco")
        .file("$name.exec")
        .asFile
}

val taskUnitTest = task<Test>("checkUnitTest") {
    useJUnitPlatform()
    testClassesDirs = sourceSets.test.get().output.classesDirs
    classpath = sourceSets.test.get().runtimeClasspath
    jvmArgs("--add-opens=java.base/java.lang=ALL-UNNAMED") // https://github.com/gradle/gradle/issues/18647
    doLast {
        getExecutionData().existing().file().filled()
    }
}

val taskCoverageReport = task<JacocoReport>("assembleCoverageReport") {
    dependsOn(taskUnitTest)
    reports {
        csv.required = false
        html.required = true
        xml.required = false
    }
    sourceDirectories.setFrom(file("src/main/kotlin"))
    classDirectories.setFrom(sourceSets.main.get().output.classesDirs)
    executionData(taskUnitTest.getExecutionData())
    doLast {
        val report = layout.buildDirectory.get()
            .dir("reports/jacoco/$name/html")
            .file("index.html")
            .asFile
            .existing()
            .file()
            .filled()
        println("Coverage report: ${report.absolutePath}")
    }
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

setOf("main", "test").also { types ->
    val configs = setOf(
        "comments",
        "common",
        "complexity",
        "coroutines",
        "empty-blocks",
        "exceptions",
        "naming",
        "performance",
        "potential-bugs",
        "style",
    ).map { config ->
        rootDir.resolve("buildSrc/src/main/resources/detekt/config/$config.yml")
            .existing()
            .file()
            .filled()
    }
    types.forEach { type ->
        val postfix = when (type) {
            "main" -> ""
            "test" -> "UnitTest"
            else -> error("Type \"$type\" is not supported!")
        }
        task<Detekt>(camelCase("check", "CodeQuality", postfix)) {
            jvmTarget = Version.JVM_TARGET
            source = sourceSets.getByName(type).allSource
            config.setFrom(configs)
            val report = layout.buildDirectory.get()
                .dir("reports/analysis/code/quality/$type/html")
                .file("index.html")
                .asFile
            reports {
                html {
                    required = true
                    outputLocation = report
                }
                md.required = false
                sarif.required = false
                txt.required = false
                xml.required = false
            }
            val detektTask = tasks.getByName<Detekt>(camelCase("detekt", type))
            classpath.setFrom(detektTask.classpath)
            doFirst {
                println("Analysis report: ${report.absolutePath}")
            }
        }
    }
}

task<Detekt>("checkDocumentation") {
    val configs = setOf(
        "common",
        "documentation",
    ).map { config ->
        rootDir.resolve("buildSrc/src/main/resources/detekt/config/$config.yml")
            .existing()
            .file()
            .filled()
    }
    jvmTarget = Version.JVM_TARGET
    source = sourceSets.main.get().allSource
    config.setFrom(configs)
    val report = layout.buildDirectory.get()
        .dir("reports/analysis/documentation/html")
        .file("index.html")
        .asFile
    reports {
        html {
            required = true
            outputLocation = report
        }
        md.required = false
        sarif.required = false
        txt.required = false
        xml.required = false
    }
    val detektTask = tasks.getByName<Detekt>("detektMain")
    classpath.setFrom(detektTask.classpath)
    doFirst {
        println("Analysis report: ${report.absolutePath}")
    }
}

"snapshot".also { variant ->
    val version = kebabCase(version.toString(), variant.uppercase(Locale.US))
    task<Jar>(camelCase("assemble", variant, "Jar")) {
        dependsOn(compileKotlinTask)
        archiveBaseName = maven.id
        archiveVersion = version
        from(compileKotlinTask.destinationDirectory.asFileTree)
    }
    task<Jar>(camelCase("assemble", variant, "Source")) {
        archiveBaseName = maven.id
        archiveVersion = version
        archiveClassifier = "sources"
        from(sourceSets.main.get().allSource)
    }
    task(camelCase("assemble", variant, "Pom")) {
        doLast {
            val file = layout.buildDirectory.get()
                .dir("libs")
                .file("${kebabCase(maven.id, version)}.pom")
                .asFile
            file.assemble(
                Maven.pom(
                    artifact = maven,
                    version = version,
                    packaging = "jar",
                ),
            )
            println("POM: ${file.absolutePath}")
        }
    }
    task(camelCase("assemble", variant, "MavenMetadata")) {
        doLast {
            val file = layout.buildDirectory.get()
                .dir("xml")
                .file("maven-metadata.xml")
                .asFile
            file.assemble(
                Maven.metadata(
                    artifact = maven,
                    version = version,
                ),
            )
            println("Maven metadata: ${file.absolutePath}")
        }
    }
    task<DokkaTask>(camelCase("assemble", variant, "Documentation")) {
        outputDirectory = layout.buildDirectory.dir("documentation/$variant")
        moduleName = gh.name
        moduleVersion = version
        dokkaSourceSets.getByName("main") {
            val path = "src/$name/kotlin"
            reportUndocumented = false
            externalDocumentationLink {
                url = URL("https://docs.gradle.org/current/javadoc/")
            }
            sourceLink {
                localDirectory = file(path)
                remoteUrl = gh.url().resolve("tree/${moduleVersion.get()}/lib", path)
            }
            jdkVersion = Version.JVM_TARGET.toInt()
        }
        doLast {
            val index = outputDirectory.get()
                .file("index.html")
                .asFile
                .existing()
                .file()
                .filled()
            println("Documentation: ${index.absolutePath}")
        }
    }
    task(camelCase("assemble", variant, "Metadata")) {
        doLast {
            val file = layout.buildDirectory.get()
                .dir("yml")
                .file("metadata.yml")
                .asFile
            file.assemble(
                """
                    repository:
                     owner: '${gh.owner}'
                     name: '${gh.name}'
                    version: '$version'
                """.trimIndent(),
            )
            println("Metadata: ${file.absolutePath}")
        }
    }
    task(camelCase("check", variant, "Readme")) {
        doLast {
            val badge = Markdown.image(
                text = "version",
                url = Badge.url(
                    label = "version",
                    message = version,
                    color = "2962ff",
                ),
            )
            val expected = setOf(
                badge,
                Markdown.link("Maven", Maven.Snapshot.url(maven, version)),
                Markdown.link("Documentation", gh.pages().resolve("doc", version)),
                "implementation(\"${colonCase(maven.group, maven.id, version)}\")",
            )
            val report = layout.buildDirectory.get()
                .dir("reports/analysis/readme")
                .file("index.html")
                .asFile
            rootDir.resolve("README.md").check(
                expected = expected,
                report = report,
            )
        }
    }
}
