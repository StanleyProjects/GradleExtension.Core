import org.gradle.api.Project
import java.io.File

fun Project.buildDir(file: File): File {
    return buildDir.resolve(file)
}

fun Project.buildDir(path: String): File {
    return buildDir(File(path))
}
