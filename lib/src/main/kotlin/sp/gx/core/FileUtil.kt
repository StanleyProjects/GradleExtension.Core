package sp.gx.core

import java.io.File

/**
 * Usage:
 * ```
 * val file = File("/tmp/bar").existing()
 * assertTrue(file.exists())
 * ```
 * @return [this] receiver file.
 * @throws IllegalStateException if [this] receiver file does not exist.
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.6
 */
fun File.existing(): File {
    check(exists()) { "Location \"$absolutePath\" does not exist!" }
    return this
}

/**
 * Usage:
 * ```
 * val file = File("/tmp/bar").file()
 * assertTrue(file.isFile)
 * ```
 * @return [this] receiver [File].
 * @throws IllegalStateException if [this] receiver [File] is not a normal file.
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.1.1
 */
fun File.file(): File {
    check(isFile) { "Location \"$absolutePath\" is not a file!" }
    return this
}

/**
 * Usage:
 * ```
 * val file = File("/tmp/bar").filled()
 * assertTrue(file.length() > 0)
 * ```
 * @return [this] receiver [File].
 * @throws IllegalStateException if [this] receiver [File] is empty.
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.1.1
 */
fun File.filled(): File {
    check(length() > 0) { "File \"$absolutePath\" is empty!" }
    return this
}

/**
 * Usage:
 * ```
 * val text = "foo"
 * val file = File("/tmp/bar").assemble(text)
 * assertEquals(text, file.readText())
 * ```
 * @receiver The [File] to which the [text] will be written.
 * @throws IllegalStateException if [text] is empty.
 * @throws IllegalStateException if [this] receiver [File] exists and not a file.
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.2.1
 */
fun File.assemble(text: String) {
    // todo org.gradle.api.file.RegularFile
    check(text.isNotEmpty())
    if (exists()) {
        check(isFile)
        check(delete())
    } else {
        parentFile?.mkdirs() ?: error("file has no parent")
    }
    writeText(text)
}

// todo task assembler
