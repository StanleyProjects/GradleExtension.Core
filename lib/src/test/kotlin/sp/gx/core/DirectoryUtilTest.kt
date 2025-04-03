package sp.gx.core

import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.nio.file.Files

internal class DirectoryUtilTest {
    @Test
    fun asFileTest() {
        val projectDir = Files.createTempDirectory("unittest").toFile()
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        check(projectDir.resolve("foo").mkdirs())
        val expected = "foobarbaz"
        projectDir.resolve("foo/bar").writeText(expected)
        val dir = project.layout.projectDirectory.dir("foo")
        assertEquals(expected, dir.asFile("bar").readText())
    }

    @Test
    fun effExistsTest() {
        val projectDir = Files.createTempDirectory("unittest").toFile()
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        check(projectDir.resolve("foo").mkdirs())
        val dir = project.layout.projectDirectory.dir("foo")
        val error = assertThrows(IllegalStateException::class.java) {
            dir.eff("bar")
        }
        assertEquals("Location \"${dir.file("bar")}\" does not exist!", error.message)
    }

    @Test
    fun effFileTest() {
        val projectDir = Files.createTempDirectory("unittest").toFile()
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        check(projectDir.resolve("foo/bar").mkdirs())
        val dir = project.layout.projectDirectory.dir("foo")
        val error = assertThrows(IllegalStateException::class.java) {
            dir.eff("bar")
        }
        assertEquals("Location \"${dir.file("bar")}\" is not a file!", error.message)
    }

    @Test
    fun effFilledTest() {
        val projectDir = Files.createTempDirectory("unittest").toFile()
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        check(projectDir.resolve("foo").mkdirs())
        val dir = project.layout.projectDirectory.dir("foo")
        projectDir.resolve("foo/bar").writeText("")
        val error = assertThrows(IllegalStateException::class.java) {
            dir.eff("bar")
        }
        assertEquals("File \"${dir.file("bar")}\" is empty!", error.message)
    }

    @Test
    fun effTest() {
        val projectDir = Files.createTempDirectory("unittest").toFile()
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        check(projectDir.resolve("foo").mkdirs())
        val dir = project.layout.projectDirectory.dir("foo")
        val expected = "foobarbaz"
        projectDir.resolve("foo/bar").writeText(expected)
        assertEquals(expected, dir.eff("bar").readText())
    }
}
