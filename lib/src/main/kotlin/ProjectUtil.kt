import org.gradle.api.Project
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

val Project.buildSrc: File get() = projectDir("buildSrc")

fun Project.buildSrc(file: File): File {
    return buildSrc.resolve(file)
}

fun Project.buildSrc(path: String): File {
    return buildSrc(File(path))
}
