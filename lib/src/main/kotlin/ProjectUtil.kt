import org.gradle.api.Project
import sp.gx.core.entity.BuildSrc
import java.io.File

fun Project.buildDir(file: File): File {
    return buildDir.resolve(file)
}

fun Project.buildDir(path: String): File {
    return buildDir(File(path))
}

fun Project.rootDir(file: File): File {
    return rootDir.resolve(file)
}

fun Project.rootDir(path: String): File {
    return rootDir(File(path))
}

fun Project.projectDir(file: File): File {
    return projectDir.resolve(file)
}

fun Project.projectDir(path: String): File {
    return projectDir(File(path))
}

val Project.buildSrc: BuildSrc get() = BuildSrc(
    projectDir = projectDir("buildSrc"),
    buildDir = projectDir.resolve("build")
)

fun Project.buildSrc(file: File): File {
    return buildSrc.projectDir.resolve(file)
}

fun Project.buildSrc(path: String): File {
    return buildSrc(File(path))
}
