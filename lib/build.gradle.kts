import java.net.URL

version = "0.2.4"

repositories {
    mavenCentral()
}

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.gradle.jacoco")
    id("io.gitlab.arturbosch.detekt") version Version.detekt
    id("org.jetbrains.dokka") version Version.dokka
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:${Version.jupiter}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${Version.jupiter}")
}

jacoco {
    toolVersion = Version.jacoco
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
    sourceDirectories.setFrom(file("src/main/kotlin"))
    classDirectories.setFrom(sourceSets.main.get().output.classesDirs)
    executionData(taskUnitTest)
    doLast {
        val report = buildDir.resolve("reports/jacoco/$name/html/index.html")
        if (report.exists()) {
            println("Coverage report: ${report.absolutePath}")
        }
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
        task<io.gitlab.arturbosch.detekt.Detekt>(camelCase("check", "CodeQuality", type)) {
            jvmTarget = Version.jvmTarget
            source = sourceSets.getByName(type).allSource
            config.setFrom(configs)
            reports {
                html {
                    required.set(true)
                    outputLocation.set(buildDir.resolve("reports/analysis/code/quality/$type/html/index.html"))
                }
                md.required.set(false)
                sarif.required.set(false)
                txt.required.set(false)
                xml.required.set(false)
            }
            val detektTask = tasks.getByName<io.gitlab.arturbosch.detekt.Detekt>(camelCase("detekt", type))
            classpath.setFrom(detektTask.classpath)
        }
    }
}

task<io.gitlab.arturbosch.detekt.Detekt>("checkDocumentation") {
    val configs = setOf(
        "common",
        "documentation",
    ).map { config ->
        rootDir.resolve("buildSrc/src/main/resources/detekt/config/$config.yml")
            .existing()
            .file()
            .filled()
    }
    jvmTarget = Version.jvmTarget
    source = sourceSets.main.get().allSource
    config.setFrom(configs)
    reports {
        html {
            required.set(true)
            outputLocation.set(buildDir.resolve("reports/analysis/documentation/html/index.html"))
        }
        md.required.set(false)
        sarif.required.set(false)
        txt.required.set(false)
        xml.required.set(false)
    }
    val detektTask = tasks.getByName<io.gitlab.arturbosch.detekt.Detekt>("detektMain")
    classpath.setFrom(detektTask.classpath)
}

"snapshot".also { variant ->
    val version = kebabCase(version.toString(), variant.toUpperCase())
    task<Jar>(camelCase("assemble", variant, "Jar")) {
        dependsOn(compileKotlinTask)
        archiveBaseName.set(Maven.artifactId)
        archiveVersion.set(version)
        from(compileKotlinTask.destinationDirectory.asFileTree)
    }
    task<Jar>(camelCase("assemble", variant, "Source")) {
        archiveBaseName.set(Maven.artifactId)
        archiveVersion.set(version)
        archiveClassifier.set("sources")
        from(sourceSets.main.get().allSource)
    }
    task(camelCase("assemble", variant, "Pom")) {
        doLast {
            buildDir.resolve("libs/${Maven.artifactId}-$version.pom").assemble(
                MavenUtil.pom(
                    groupId = Maven.groupId,
                    artifactId = Maven.artifactId,
                    version = version,
                    packaging = "jar",
                ),
            )
        }
    }
    task(camelCase("assemble", variant, "MavenMetadata")) {
        doLast {
            buildDir.resolve("xml/maven-metadata.xml").assemble(
                MavenUtil.metadata(
                    groupId = Maven.groupId,
                    artifactId = Maven.artifactId,
                    version = version,
                ),
            )
        }
    }
    task<org.jetbrains.dokka.gradle.DokkaTask>(camelCase("assemble", variant, "Documentation")) {
        outputDirectory.set(buildDir.resolve("documentation/$variant"))
        moduleName.set(Repository.name)
        moduleVersion.set(version)
        dokkaSourceSets.getByName("main") {
            val path = "src/$name/kotlin"
            reportUndocumented.set(false)
            sourceLink {
                localDirectory.set(file(path))
                val url = GitHubUtil.url(Repository.owner, Repository.name)
                remoteUrl.set(URL("$url/tree/${moduleVersion.get()}/lib/$path"))
            }
            jdkVersion.set(Version.jvmTarget.toInt())
        }
    }
    task(camelCase("assemble", variant, "Metadata")) {
        doLast {
            buildDir.resolve("yml/metadata.yml").assemble(
                """
                    repository:
                     owner: '${Repository.owner}'
                     name: '${Repository.name}'
                    version: '$version'
                """.trimIndent(),
            )
        }
    }
    task(camelCase("check", variant, "Readme")) {
        doLast {
            val badge = MarkdownUtil.image(
                text = "version",
                url = BadgeUtil.url(
                    label = "version",
                    message = version,
                    color = "2962ff",
                ),
            )
            val expected = setOf(
                badge,
                MarkdownUtil.url("Maven", MavenUtil.Snapshot.url(Maven.groupId, Maven.artifactId, version)),
                MarkdownUtil.url("Documentation", GitHubUtil.pages(Repository.owner, Repository.name, "doc/$version")),
                "implementation(\"${Maven.groupId}:${Maven.artifactId}:$version\")",
            )
            rootDir.resolve("README.md").check(
                expected = expected,
                report = buildDir.resolve("reports/analysis/readme/index.html"),
            )
        }
    }
}
