package sp.gx.core

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
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
        assertEquals(expected, actual)
        assertEquals(expected.absolutePath, actual.absolutePath)
        assertEquals(expected.length(), actual.length())
        assertEquals(expected.readBytes().size, actual.readBytes().size)
        assertEquals(expected.readText().length, actual.readText().length)
        assertEquals(expected.readText(), actual.readText())
    }

    @Test
    fun filledNotTest() {
        val expected = File.createTempFile("foo", "bar")
        @Suppress("IgnoredReturnValue")
        expected.createNewFile()
        check(expected.exists())
        check(expected.length() == 0L)
        assertThrows(IllegalStateException::class.java) {
            @Suppress("IgnoredReturnValue")
            expected.filled()
        }
    }

    @Test
    fun fileTest() {
        val expected = File.createTempFile("foo", "bar")
        check(expected.exists())
        check(expected.isFile)
        val actual = expected.file()
        assertEquals(expected, actual)
        assertEquals(expected.absolutePath, actual.absolutePath)
        assertEquals(expected.length(), actual.length())
        assertEquals(expected.readBytes().size, actual.readBytes().size)
        assertEquals(expected.readText().length, actual.readText().length)
        assertEquals(expected.readText(), actual.readText())
    }

    @Test
    fun fileNotTest() {
        val expected = File.createTempFile("foo", "bar")
        check(expected.delete())
        check(!expected.exists())
        check(expected.mkdir())
        check(expected.exists())
        check(expected.isDirectory)
        check(!expected.isFile)
        assertThrows(IllegalStateException::class.java) {
            @Suppress("IgnoredReturnValue")
            expected.file()
        }
    }

    @Test
    fun checkTest() {
        val file = File.createTempFile("foo", "bar")
        val expected = setOf(
            "foo",
            "bar",
            "baz",
        )
        expected.forEach {
            file.appendText(it)
        }
        expected.forEach {
            check(file.readText().contains(it))
        }
        val report = File.createTempFile("foo", "report")
        file.check(expected = expected, report = report)
        assertTrue(report.exists())
        assertEquals("All checks of the file along the \"${file.name}\" were successful.", report.readText())
    }

    @Test
    fun checkErrorTest() {
        val file = File.createTempFile("foo", "bar")
        file.appendText("foo")
        check(file.readText().contains("foo"))
        val unexpected = setOf("bar")
        unexpected.forEach {
            check(!file.readText().contains(it))
        }
        val report = File.createTempFile("foo", "report")
        assertThrows(IllegalStateException::class.java) {
            file.check(expected = unexpected, report = report)
        }
        assertTrue(report.exists())
        unexpected.forEach {
            assertTrue(report.readText().contains("the file does not contain \"$it\" line"))
        }
    }

    @Test
    fun checkNoneTest() {
        val file = File.createTempFile("foo", "bar")
        file.delete()
        check(!file.exists())
        val report = File.createTempFile("foo", "report")
        assertThrows(IllegalStateException::class.java) {
            file.check(expected = setOf("foo"), report = report)
        }
        assertTrue(report.exists())
        assertTrue(report.readText().contains("the file does not exist"))
    }

    @Test
    fun checkEmptyTest() {
        val file = File.createTempFile("foo", "bar")
        check(file.exists())
        check(file.length() == 0L)
        val report = File.createTempFile("foo", "report")
        assertThrows(IllegalStateException::class.java) {
            file.check(expected = setOf("foo"), report = report)
        }
        assertTrue(report.exists())
        assertTrue(report.readText().contains("the file does not contain text"))
    }

    @Test
    fun checkDirTest() {
        val file = File.createTempFile("foo", "bar")
        file.delete()
        file.mkdir()
        check(file.exists())
        check(file.isDirectory)
        val report = File.createTempFile("foo", "report")
        assertThrows(IllegalStateException::class.java) {
            file.check(expected = setOf("foo"), report = report)
        }
        assertTrue(report.exists())
        assertTrue(report.readText().contains("the file is a directory"))
    }

    @Test
    fun checkReportTest() {
        val file = File.createTempFile("foo", "bar")
        file.writeText("foo")
        File.createTempFile("foo", "report").also { report ->
            report.delete()
            check(!report.exists())
            file.check(expected = setOf("foo"), report = report)
            assertTrue(report.exists())
        }
        File.createTempFile("foo", "report").also { report ->
            check(report.exists())
            file.check(expected = setOf("foo"), report = report)
            assertTrue(report.exists())
        }
        File.createTempFile("foo", "report").also { path ->
            path.delete()
            path.mkdir()
            val report = path.resolve("report")
            report.delete()
            check(path.exists())
            check(path.isDirectory)
            check(!report.exists())
            file.check(expected = setOf("foo"), report = report)
            assertTrue(report.exists())
        }
        File.createTempFile("foo", "report").also { path ->
            path.delete()
            path.mkdir()
            val report = path.resolve("report")
            report.delete()
            path.delete()
            check(!path.exists())
            check(!report.exists())
            report.parentFile
            file.check(expected = setOf("foo"), report = report)
            assertTrue(report.exists())
        }
        File("/").also { report ->
            check(report.exists())
            check(report.isDirectory)
            assertThrows(IllegalStateException::class.java) {
                file.check(expected = setOf("foo"), report = report)
            }
        }
    }
}
