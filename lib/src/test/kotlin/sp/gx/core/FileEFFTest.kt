package sp.gx.core

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.nio.file.Files

internal class FileEFFTest {
    @Test
    fun effExistsTest() {
        val dir = Files.createTempDirectory("unittest").toFile()
        val issue = dir.resolve("foo")
        val error = assertThrows(IllegalStateException::class.java) {
            issue.eff()
        }
        assertEquals("Location \"${issue.absolutePath}\" does not exist!", error.message)
    }

    @Test
    fun effFileTest() {
        val dir = Files.createTempDirectory("unittest").toFile()
        val issue = dir.resolve("foo")
        issue.mkdirs()
        val error = assertThrows(IllegalStateException::class.java) {
            issue.eff()
        }
        assertEquals("Location \"${issue.absolutePath}\" is not a file!", error.message)
    }

    @Test
    fun effFilledTest() {
        val dir = Files.createTempDirectory("unittest").toFile()
        val issue = dir.resolve("foo")
        issue.writeText("")
        val error = assertThrows(IllegalStateException::class.java) {
            issue.eff()
        }
        assertEquals("File \"${issue.absolutePath}\" is empty!", error.message)
    }

    @Test
    fun effTest() {
        val dir = Files.createTempDirectory("unittest").toFile()
        val issue = dir.resolve("foo")
        val expected = "foobarbaz"
        issue.writeText(expected)
        assertEquals(expected, issue.eff().readText())
    }
}
