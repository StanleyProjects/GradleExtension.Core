import java.io.File

object FileUtil {
    private fun File.findIssues(expected: Set<String>): Set<String> {
        if (!exists()) return setOf("the file does not exist")
        if (isDirectory) return setOf("the file is a directory")
        val result = mutableSetOf<String>()
        val lines = readLines(Charsets.UTF_8).map { it.trim() }
        if (lines.isEmpty()) return setOf("the file does not contain text")
        expected.map { it.trim() }.forEach {
            if (lines.none { actual -> actual.contains(it) }) {
                result.add("the file does not contain \"$it\" line")
            }
        }
        return result
    }

    fun check(file: File, expected: Set<String>, report: File) {
        val issues = file.findIssues(expected)
        if (report.exists()) {
            report.delete()
        } else {
            report.parentFile?.mkdirs()
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
}
