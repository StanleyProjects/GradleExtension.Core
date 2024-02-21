import sp.gx.core.check

buildscript {
    repositories.mavenCentral()

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.KOTLIN}")
    }
}

task<Delete>("clean") {
    delete = setOf(layout.buildDirectory.get(), "buildSrc/build")
}

task("checkLicense") {
    doLast {
        val author = "Stanley Wintergreen" // todo
        val report =
            layout.buildDirectory.get()
                .dir("reports/analysis/license")
                .file("index.html")
                .asFile
        file("LICENSE").check(
            expected = emptySet(),
            regexes = setOf("^Copyright 2\\d{3} $author${'$'}".toRegex()),
            report = report,
        )
    }
}

repositories.mavenCentral()

val ktlint: Configuration by configurations.creating

dependencies {
    ktlint("com.pinterest.ktlint:ktlint-cli:${Version.KTLINT}") {
        attributes {
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }
}

task<JavaExec>("checkCodeStyle") {
    classpath = ktlint
    mainClass = "com.pinterest.ktlint.Main"
    val reporter = "html"
    val output = layout.buildDirectory.get()
        .dir("reports/analysis/code/style/html")
        .file("index.html")
        .asFile
    args(
        "build.gradle.kts",
        "settings.gradle.kts",
        "buildSrc/src/main/kotlin/**/*.kt",
        "buildSrc/build.gradle.kts",
        "lib/src/main/kotlin/**/*.kt",
        "lib/src/test/kotlin/**/*.kt",
        "lib/build.gradle.kts",
        "--reporter=$reporter,output=${output.absolutePath}",
    )
}
