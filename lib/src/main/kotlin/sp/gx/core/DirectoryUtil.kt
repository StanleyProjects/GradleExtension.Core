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
