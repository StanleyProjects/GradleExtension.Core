package sp.gx.core

import org.gradle.internal.FileUtils
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.nio.file.Files

internal class BuildSrcTest {
    @Test
    fun projectDirectoryTest() {
        val projectDir = Files.createTempDirectory("unittest").toFile().let {
            FileUtils.canonicalize(it)
        }
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        val expected = projectDir.resolve("buildSrc").absolutePath
        val actual = project.buildSrc.projectDirectory.asFile.absolutePath
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun buildDirectoryTest() {
        val projectDir = Files.createTempDirectory("unittest").toFile().let {
            FileUtils.canonicalize(it)
        }
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        val expected = projectDir.resolve("buildSrc/build").absolutePath
        val actual = project.buildSrc.buildDirectory.get().asFile.absolutePath
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun fileTest() {
        TODO("BuildSrcTest:fileTest")
    }

    @Test
    fun dirTest() {
        TODO("BuildSrcTest:dirTest")
    }

    @Test
    fun filesTest() {
        TODO("BuildSrcTest:filesTest")
    }
}
