package sp.gx.core

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.io.File

internal class FileUtilTest {
    @Test
    fun existingTest() {
        val file = File.createTempFile("foo", "bar")
        check(file.exists())
        assertEquals(file, file.existing())
    }

    @Test
    fun existingNotTest() {
        val file = File("foo")
        check(!file.exists())
        assertThrows(IllegalStateException::class.java) {
            @Suppress("IgnoredReturnValue")
            file.existing()
        }
    }

    @Test
    fun filledTest() {
        val expected = File.createTempFile("foo", "bar")
        expected.writeText("baz")
        check(expected.length() > 0)
        check(expected.readBytes().isNotEmpty())
        check(expected.readText().isNotEmpty())
        val actual = expected.filled()
        assertEquals(expected, actual.filled())
        assertEquals(expected.absolutePath, actual.absolutePath)
        assertEquals(expected.length(), actual.length())
        assertEquals(expected.readBytes().size, actual.readBytes().size)
        assertEquals(expected.readText().length, actual.readText().length)
        assertEquals(expected.readText(), actual.readText())
    }

    @Test
    fun filledNotTest() {
        val expected = File.createTempFile("foo", "bar")
        expected.createNewFile()
        check(expected.exists())
        check(expected.length() == 0L)
        assertThrows(IllegalStateException::class.java) {
            @Suppress("IgnoredReturnValue")
            expected.filled()
        }
    }
}
