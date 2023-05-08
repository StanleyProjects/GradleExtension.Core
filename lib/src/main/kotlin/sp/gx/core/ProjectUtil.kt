package sp.gx.core

import org.gradle.api.Project
import java.io.File

/**
 * @param file The relative path inside the project's build directory.
 * @return The path to the file inside the project's build directory.
 * @see Project.getBuildDir [docs.gradle.org](https://docs.gradle.org/current/javadoc/org/gradle/api/Project.html#getBuildDir)
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.4
 */
fun Project.buildDir(file: File): File {
    return buildDir.resolve(file)
}

/**
 * @param path The relative path of a file inside the project's build directory.
 * @return The path to the file inside the project's build directory.
 * @see Project.getBuildDir [docs.gradle.org](https://docs.gradle.org/current/javadoc/org/gradle/api/Project.html#getBuildDir)
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.4
 */
fun Project.buildDir(path: String): File {
    return buildDir(File(path))
}

/**
 * @param file The relative path inside the root directory.
 * @return The path to the file inside the root directory.
 * @see Project.getRootDir [docs.gradle.org](https://docs.gradle.org/current/javadoc/org/gradle/api/Project.html#getRootDir)
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.4
 */
fun Project.rootDir(file: File): File {
    return rootDir.resolve(file)
}

/**
 * @param path The relative path of a file inside the root directory.
 * @return The path to the file inside the root directory.
 * @see Project.getRootDir [docs.gradle.org](https://docs.gradle.org/current/javadoc/org/gradle/api/Project.html#getRootDir)
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.4
 */
fun Project.rootDir(path: String): File {
    return rootDir(File(path))
}

/**
 * @param file The relative path inside the project's directory.
 * @return The path to the file inside the project's directory.
 * @see Project.getProjectDir [docs.gradle.org](https://docs.gradle.org/current/javadoc/org/gradle/api/Project.html#getProjectDir)
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.4
 */
fun Project.projectDir(file: File): File {
    return projectDir.resolve(file)
}

/**
 * @param path The relative path of a file inside the project's directory.
 * @return The path to the file inside the project's directory.
 * @see Project.getProjectDir [docs.gradle.org](https://docs.gradle.org/current/javadoc/org/gradle/api/Project.html#getProjectDir)
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.4
 */
fun Project.projectDir(path: String): File {
    return projectDir(File(path))
}

/**
 * The project's 'buildSrc' directory.
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @see Project [docs.gradle.org](https://docs.gradle.org/current/javadoc/org/gradle/api/Project.html)
 * @since 0.0.4
 */
val Project.buildSrc: File get() = projectDir("buildSrc")

/**
 * @param file The relative path inside the project's 'buildSrc' directory.
 * @return The path to the file inside the project's 'buildSrc' directory.
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.4
 */
fun Project.buildSrc(file: File): File {
    return buildSrc.resolve(file)
}

/**
 * @param path The relative path of a file inside the project's 'buildSrc' directory.
 * @return The path to the file inside the project's 'buildSrc' directory.
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.4
 */
fun Project.buildSrc(path: String): File {
    return buildSrc(File(path))
}
