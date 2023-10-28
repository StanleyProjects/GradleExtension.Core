package sp.gx.core

import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

@Suppress("StringLiteralDuplication")
internal class RegularFileUtilTest {
    @Test
    fun existingTest() {
        val project = ProjectBuilder.builder().build()
        val dir = project.layout.buildDirectory.get()
        val regularFile = dir.file("foobar")
        val file = regularFile.asFile
        check(!file.exists())
        file.parentFile.mkdirs()
        file.writeText("barbaz")
        check(file.exists())
        assertEquals(file, regularFile.existing())
    }

    @Test
    fun existingNotTest() {
        val project = ProjectBuilder.builder().build()
        val dir = project.layout.buildDirectory.get()
        val regularFile = dir.file("foobar")
        val file = regularFile.asFile
        check(!file.exists())
        assertThrows(IllegalStateException::class.java) {
            @Suppress("IgnoredReturnValue")
            regularFile.existing()
        }
    }

    @Test
    fun assembleTest() {
        val project = ProjectBuilder.builder().build()
        val dir = project.layout.buildDirectory.get()
        val regularFile = dir.file("foobar")
        val expected = "foobar"
        val file = regularFile.assemble(expected)
        assertEquals(expected, file.readText())
    }

    @Test
    fun assembleNoneTest() {
        val project = ProjectBuilder.builder().build()
        val dir = project.layout.buildDirectory.get()
        val regularFile = dir.file("foobar")
        check(!regularFile.asFile.exists())
        val expected = "foobar"
        val file = regularFile.assemble(expected)
        assertEquals(expected, file.readText())
    }

    @Test
    fun assembleDirTest() {
        val project = ProjectBuilder.builder().build()
        val dir = project.layout.buildDirectory.get()
        val regularFile = dir.file("foobar")
        val file = regularFile.asFile
        check(!file.exists())
        check(file.mkdirs())
        check(file.exists())
        check(file.isDirectory)
        assertThrows(IllegalStateException::class.java) {
            regularFile.assemble("foo")
        }
    }

    @Test
    fun assembleEmptyTest() {
        val project = ProjectBuilder.builder().build()
        val dir = project.layout.buildDirectory.get()
        val regularFile = dir.file("foobar")
        assertThrows(IllegalStateException::class.java) {
            regularFile.assemble("")
        }
    }
}
