package sp.gx.core

import java.io.File

/**
 * Usage:
 * ```
 * File("/tmp/bar").check(
 *     expected = setOf("foo", "bar"),
 *     report = File("/tmp/report"),
 * )
 * ```
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
    check(expected = expected, regexes = emptySet(), report = report)
}

fun File.check(
    expected: Set<String>,
    regexes: Set<Regex>,
    report: File,
) {
    // todo task
    val issues = when {
        !exists() -> setOf("the file does not exist")
        isDirectory -> setOf("the file is a directory")
        else -> {
            val actual = readLines(Charsets.UTF_8)
            if (actual.isEmpty()) {
                setOf("the file does not contain text")
            } else {
                checkLines(actual = actual, expected = expected) + checkRegexes(actual = actual, regexes = regexes)
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

private fun checkLines(actual: List<String>, expected: Set<String>): Set<String> {
    return expected.mapNotNull { line ->
        if (actual.none { it.contains(line) }) {
            "the file does not contain \"$line\" line"
        } else {
            null
        }
    }.toSet()
}

private fun checkRegexes(actual: List<String>, regexes: Set<Regex>): Set<String> {
    return regexes.mapNotNull { regex ->
        if (actual.none { regex matches it }) {
            "the file does not match \"$regex\" regex"
        } else {
            null
        }
    }.toSet()
}
