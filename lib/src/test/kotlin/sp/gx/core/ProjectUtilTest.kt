package sp.gx.core

import org.gradle.internal.FileUtils
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.nio.file.Files

internal class ProjectUtilTest {
    @Test
    fun buildDirTest() {
        val projectDir = Files.createTempDirectory("ProjectUtilTest:buildDirTest").toFile().let {
            FileUtils.canonicalize(it)
        }
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        Assertions.assertEquals(projectDir.resolve("build").absolutePath, project.buildDir().asFile.absolutePath)
    }

    @Test
    fun buildDirLayoutTest() {
        val projectDir = Files.createTempDirectory("ProjectUtilTest:buildDirLayoutTest").toFile().let {
            FileUtils.canonicalize(it)
        }
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        Assertions.assertEquals(projectDir.resolve("build").absolutePath, project.layout.buildDir().asFile.absolutePath)
    }

    @Test
    fun dirLayoutTest() {
        val projectDir = Files.createTempDirectory("ProjectUtilTest:dirLayoutTest").toFile().let {
            FileUtils.canonicalize(it)
        }
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        Assertions.assertEquals(projectDir.resolve("foo").absolutePath, project.layout.dir("foo").asFile.absolutePath)
    }
}
