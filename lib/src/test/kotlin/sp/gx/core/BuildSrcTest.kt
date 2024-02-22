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
        val buildSrc = project.buildSrc
        Assertions.assertEquals(projectDir.resolve("buildSrc").absolutePath, buildSrc.projectDirectory.asFile.absolutePath)
    }

    @Test
    fun buildDirectoryTest() {
        val projectDir = Files.createTempDirectory("unittest").toFile().let {
            FileUtils.canonicalize(it)
        }
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        val buildSrc = project.buildSrc
        Assertions.assertEquals(projectDir.resolve("buildSrc/build").absolutePath, buildSrc.buildDirectory.get().asFile.absolutePath)
    }
}
