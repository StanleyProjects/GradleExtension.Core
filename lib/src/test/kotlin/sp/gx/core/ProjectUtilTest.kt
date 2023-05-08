package sp.gx.core

import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import java.io.File

internal class ProjectUtilTest {
    @Test
    fun buildDirTest() {
        val project = ProjectBuilder.builder().build()
        val expected = "foo"
        val unexpected = "bar"
        assertNotEquals(unexpected, expected)
        assertEquals(project.buildDir(expected), File(project.buildDir, expected))
        assertNotEquals(project.buildDir(unexpected), File(project.buildDir, expected))
    }

    @Test
    fun rootDirTest() {
        val project = ProjectBuilder.builder().build()
        val expected = "foo"
        val unexpected = "bar"
        assertNotEquals(unexpected, expected)
        assertEquals(project.rootDir(expected), File(project.rootDir, expected))
        assertNotEquals(project.rootDir(unexpected), File(project.rootDir, expected))
    }

    @Test
    fun buildSrcTest() {
        val project = ProjectBuilder.builder().build()
        val expected = "foo"
        val unexpected = "bar"
        assertNotEquals(unexpected, expected)
        assertEquals(project.buildSrc(expected), project.rootDir.resolve("buildSrc").resolve(expected))
        assertNotEquals(project.buildSrc(unexpected), project.rootDir.resolve("buildSrc").resolve(expected))
    }
}
