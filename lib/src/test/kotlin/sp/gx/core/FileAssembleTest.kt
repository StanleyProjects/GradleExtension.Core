package sp.gx.core

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File

internal class FileAssembleTest {
    @Test
    fun assembleTest() {
        val file = File.createTempFile("foo", "bar")
        check(file.length() == 0L)
        val expected = "foobar"
        file.assemble(expected)
        Assertions.assertEquals(expected, file.readText())
    }

    @Test
    fun assembleNoneTest() {
        val file = File.createTempFile("foo", "bar")
        check(file.delete())
        check(!file.exists())
        val expected = "foobar"
        file.assemble(expected)
        Assertions.assertEquals(expected, file.readText())
    }

    @Test
    fun assembleDirTest() {
        val file = File.createTempFile("foo", "bar")
        check(file.delete())
        check(file.mkdir())
        check(file.exists())
        check(file.isDirectory)
        Assertions.assertThrows(IllegalStateException::class.java) {
            file.assemble("foo")
        }
    }

    @Test
    fun assembleEmptyTest() {
        val file = File.createTempFile("foo", "bar")
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            file.assemble("")
        }
    }
}
