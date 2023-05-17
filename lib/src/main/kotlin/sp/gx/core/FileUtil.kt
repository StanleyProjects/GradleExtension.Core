package sp.gx.core

import java.io.File

/**
 * @return [this] receiver file.
 * @throws IllegalStateException if [this] receiver file does not exist.
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.6
 */
fun File.existing(): File {
    check(exists())
    return this
}

/**
 * Usage:
 * ```
 * val foo = File("/tmp/bar").existing().file().filled()
 * ```
 * @return [this] receiver [File].
 * @throws IllegalStateException if [this] receiver [File] is not a normal file.
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.1.1
 */
fun File.file(): File {
    check(isFile)
    return this
}

/**
 * @return [this] receiver [File].
 * @throws IllegalStateException if [this] receiver [File] is empty.
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.1.1
 */
fun File.filled(): File {
    check(length() > 0)
    return this
}

/**
 * @receiver The [File] whose contents will be checked.
 * @param expected The set of strings expected to be in [this] receiver [File].
 * @param report Where the test result will be written.
 * @throws IllegalStateException if [this] receiver [File] does not exist.
 * @throws IllegalStateException if [this] receiver [File] is a directory.
 * @throws IllegalStateException if [this] receiver [File] is empty.
 * @throws IllegalStateException if [this] receiver [File] does not contain text.
 * @throws IllegalStateException if [report] exists and not a file.
 * @throws IllegalStateException if problems are found while checking [this] receiver [File].
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.2.0
 */
fun File.check(expected: Set<String>, report: File) {
    val issues = when {
        !exists() -> setOf("the file does not exist")
        isDirectory -> setOf("the file is a directory")
        else -> {
            val actual = readLines(Charsets.UTF_8)
            if (actual.isEmpty()) {
                setOf("the file does not contain text")
            } else {
                expected.mapNotNull { line ->
                    if (actual.none { it.contains(line) }) {
                        "the file does not contain \"$line\" line"
                    } else {
                        null
                    }
                }.toSet()
            }
        }
    }
    if (report.exists()) {
        check(report.isFile) { "report is not a file" }
        check(report.delete())
    } else {
        report.parentFile?.mkdirs() ?: error("report has no parent")
    }
    if (issues.isEmpty()) {
        val message = "All checks of the file along the \"$name\" were successful."
        report.writeText(message)
        @Suppress("ForbiddenMethodCall")
        println(message)
        return
    }
    val text = """
        <html>
        <h3>The following problems were found while checking the <code>$name</code>:</h3>
        ${issues.joinToString(prefix = "<ul>", postfix = "</ul>", separator = "\n") { "<li>$it</li>" }}
        </html>
    """.trimIndent()
    report.writeText(text)
    error("Problems were found while checking the \"$name\". See the report ${report.absolutePath}")
}
