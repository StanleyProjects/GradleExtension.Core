package sp.gx.core

import org.gradle.api.Project
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

fun checkFile(name: String, file: File, expected: Set<String>, report: File) {
    val issues = when {
        !file.exists() -> setOf("the file does not exist")
        file.isDirectory -> setOf("the file is a directory")
        else -> {
            val actual = file.readLines(Charsets.UTF_8)
            when {
                actual.isEmpty() -> setOf("the file does not contain text")
                else -> {
                    expected.mapNotNull { e ->
                        "the file does not contain \"$e\" line".takeIf { _ ->
                            actual.none { it.contains(e) }
                        }
                    }.toSet()
                }
            }
        }
    }
    when {
        report.exists() -> report.delete()
        else -> report.parentFile?.mkdirs()
    }
    if (issues.isEmpty()) {
        val message = "All checks of the file along the \"${file.name}\" were successful."
        report.writeText(message)
        println(message)
        return
    }
    val text = """
        <html>
        <h3>The following problems were found while checking the <code>${file.name}</code>:</h3>
        ${issues.joinToString(prefix = "<ul>", postfix = "</ul>", separator = "\n") { "<li>$it</li>" }}
        </html>
    """.trimIndent()
    report.writeText(text)
    error("Problems were found while checking the \"${file.name}\". See the report ${report.absolutePath}")
}
