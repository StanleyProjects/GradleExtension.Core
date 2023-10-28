package sp.gx.core

import org.gradle.api.file.RegularFile
import java.io.File

/**
 * Usage:
 * ```
 * val file = layout.buildDirectory.get()
 *     .dir("foo")
 *     .file("bar")
 *     .existing()
 * assertTrue(file.exists())
 * ```
 * @return [File] of [this] receiver [RegularFile].
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.4.5
 */
fun RegularFile.existing(): File {
    return asFile.existing()
}

/**
 * Usage:
 * ```
 * val text = "foo"
 * val file = layout.buildDirectory.get()
 *     .dir("bar")
 *     .file("baz")
 *     .assemble(text)
 * assertEquals(text, file.readText())
 * ```
 * @receiver The [RegularFile] to which the [text] will be written.
 * @return The [File] of [this] receiver [RegularFile].
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.4.5
 */
fun RegularFile.assemble(text: String): File {
    val file = asFile
    file.assemble(text)
    return file
}
