buildscript {
    repositories.mavenCentral()

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}")
    }
}

task<Delete>("clean") {
    delete = setOf(buildDir, "buildSrc/build")
}

repositories.mavenCentral()

val ktlint: Configuration by configurations.creating

dependencies {
    ktlint("com.pinterest:ktlint:${Version.ktlint}") {
        attributes {
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }
}

task<JavaExec>("checkCodeStyle") {
    classpath = ktlint
    mainClass.set("com.pinterest.ktlint.Main")
    args(
        "build.gradle.kts",
        "settings.gradle.kts",
        "buildSrc/src/main/kotlin/**/*.kt",
        "buildSrc/build.gradle.kts",
        "lib/src/main/kotlin/**/*.kt",
        "lib/src/test/kotlin/**/*.kt",
        "lib/build.gradle.kts",
        "--reporter=html,output=${File(buildDir, "reports/analysis/code/style/html/index.html")}",
    )
}

task("checkLicense") {
    doLast {
        FileUtil.check(
            file = rootDir.resolve("LICENSE"),
            expected = emptySet(), // todo author
            report = buildDir.resolve("reports/analysis/license/index.html")
        )
    }
}
