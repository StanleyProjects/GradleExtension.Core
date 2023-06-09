package sp.gx.core

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File

internal class FileCheckTest {
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
        val report = File.createTempFile("foo", "baz")
        file.check(expected = expected, report = report)
        Assertions.assertTrue(report.exists())
        Assertions.assertEquals("All checks of the file along the \"${file.name}\" were successful.", report.readText())
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
        val report = File.createTempFile("foo", "baz")
        Assertions.assertThrows(IllegalStateException::class.java) {
            file.check(expected = unexpected, report = report)
        }
        Assertions.assertTrue(report.exists())
        unexpected.forEach {
            Assertions.assertTrue(report.readText().contains("the file does not contain \"$it\" line"))
        }
    }

    @Test
    fun checkNoneTest() {
        val file = File.createTempFile("foo", "bar")
        check(file.delete())
        check(!file.exists())
        val report = File.createTempFile("foo", "baz")
        Assertions.assertThrows(IllegalStateException::class.java) {
            file.check(expected = setOf("foo"), report = report)
        }
        Assertions.assertTrue(report.exists())
        Assertions.assertTrue(report.readText().contains("the file does not exist"))
    }

    @Test
    fun checkEmptyTest() {
        val file = File.createTempFile("foo", "bar")
        check(file.exists())
        check(file.length() == 0L)
        val report = File.createTempFile("foo", "baz")
        Assertions.assertThrows(IllegalStateException::class.java) {
            file.check(expected = setOf("foo"), report = report)
        }
        Assertions.assertTrue(report.exists())
        Assertions.assertTrue(report.readText().contains("the file does not contain text"))
    }

    @Test
    fun checkDirTest() {
        val file = File.createTempFile("foo", "bar")
        check(file.delete())
        check(file.mkdir())
        check(file.exists())
        check(file.isDirectory)
        val report = File.createTempFile("foo", "baz")
        Assertions.assertThrows(IllegalStateException::class.java) {
            file.check(expected = setOf("foo"), report = report)
        }
        Assertions.assertTrue(report.exists())
        Assertions.assertTrue(report.readText().contains("the file is a directory"))
    }

    @Test
    fun checkReportTest() {
        val file = File.createTempFile("foo", "bar")
        file.writeText("foo")
        File.createTempFile("foo", "baz").also { report ->
            check(report.delete())
            check(!report.exists())
            file.check(expected = setOf("foo"), report = report)
            Assertions.assertTrue(report.exists())
        }
        File.createTempFile("foo", "baz").also { report ->
            check(report.exists())
            file.check(expected = setOf("foo"), report = report)
            Assertions.assertTrue(report.exists())
        }
        File.createTempFile("foo", "baz").also { path ->
            check(path.delete())
            check(path.mkdir())
            val report = path.resolve("baz")
            check(path.exists())
            check(path.isDirectory)
            check(!report.exists())
            file.check(expected = setOf("foo"), report = report)
            Assertions.assertTrue(report.exists())
        }
        File.createTempFile("foo", "baz").also { path ->
            check(path.delete())
            check(path.mkdir())
            val report = path.resolve("baz")
            check(path.delete())
            check(!path.exists())
            check(!report.exists())
            report.parentFile
            file.check(expected = setOf("foo"), report = report)
            Assertions.assertTrue(report.exists())
        }
        File("/").also { report ->
            check(report.exists())
            check(report.isDirectory)
            Assertions.assertThrows(IllegalStateException::class.java) {
                file.check(expected = setOf("foo"), report = report)
            }
        }
    }
}
