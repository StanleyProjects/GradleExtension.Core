package sp.gx.core

import org.gradle.api.file.Directory
import java.io.File

/**
 * Usage:
 * ```
 * val project: Project = ...
 * val file = project.layout.projectDirectory.dir("foo").asFile("bar")
 * assertTrue(file.exists())
 * ```
 * @return a [File] whose value is a [Directory] whose location is the given path resolved relative to this directory.
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.5.4
 * @see [Directory.file]
 */
fun Directory.asFile(path: String): File {
    return file(path).asFile
}

/**
 * Usage:
 * ```
 * val project: Project = ...
 * val file = project.layout.projectDirectory.eff("foo")
 * assertTrue(file.exists())
 * assertTrue(file.isFile)
 * assertTrue(file.length() > 0)
 * ```
 *
 * @return a [File] whose value is a [Directory] whose location is the given path resolved relative to this directory.
 * @throws IllegalStateException if target [File] does not exist.
 * @throws IllegalStateException if target [File] is not a normal file.
 * @throws IllegalStateException if target [File] is empty.
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.6.1
 */
fun Directory.eff(path: String): File {
    val file = file(path).asFile
    if (!file.exists()) {
        error("Location \"${file.absolutePath}\" does not exist!")
    }
    if (!file.isFile) {
        error("Location \"${file.absolutePath}\" is not a file!")
    }
    if (file.length() == 0L) {
        error("File \"${file.absolutePath}\" is empty!")
    }
    return file
}
