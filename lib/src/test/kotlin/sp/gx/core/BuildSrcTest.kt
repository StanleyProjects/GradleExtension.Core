package sp.gx.core

import org.gradle.api.internal.provider.DefaultProvider
import org.gradle.internal.FileUtils
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File
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
        val projectDir = Files.createTempDirectory("unittest").toFile().let {
            FileUtils.canonicalize(it)
        }
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        val expected = "foobarbaz"
        projectDir.resolve("buildSrc").mkdirs()
        projectDir.resolve("buildSrc").resolve("foo").writeText(expected)
        val expectedFile = projectDir.resolve("buildSrc").resolve("foo")
        val actualFile = project.buildSrc.file(DefaultProvider { File("foo") }).get().asFile
        Assertions.assertEquals(expectedFile.absolutePath, actualFile.absolutePath)
        Assertions.assertEquals(expected, actualFile.readText())
    }

    @Test
    fun dirTest() {
        val projectDir = Files.createTempDirectory("unittest").toFile().let {
            FileUtils.canonicalize(it)
        }
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        projectDir.resolve("buildSrc").mkdirs()
        val expected = projectDir.resolve("buildSrc/foo/bar").absolutePath
        val provider = DefaultProvider { File("foo/bar") }
        val actual = project.buildSrc.dir(provider).get().asFile.absolutePath
        Assertions.assertEquals(expected, actual)
        Assertions.assertFalse(project.buildSrc.dir(provider).get().asFile.exists())
        Assertions.assertFalse(project.buildSrc.dir(provider).get().asFile.isDirectory)
        projectDir.resolve("buildSrc/foo/bar").mkdirs()
        Assertions.assertTrue(project.buildSrc.dir(provider).get().asFile.exists())
        Assertions.assertTrue(project.buildSrc.dir(provider).get().asFile.isDirectory)
    }

    @Test
    fun filesTest() {
        val projectDir = Files.createTempDirectory("unittest").toFile().let {
            FileUtils.canonicalize(it)
        }
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        val texts = mapOf(
            "foo" to "footext",
            "bar" to "bartext",
            "baz" to "baztext",
        )
        check(texts.values.size == 3)
        check(texts.values.size == texts.values.toSet().size)
        projectDir.resolve("buildSrc").mkdirs()
        texts.forEach { (path, text) ->
            projectDir.resolve("buildSrc").resolve(path).writeText(text)
        }
        project.buildSrc.files(texts.keys).forEach { file ->
            val expected = texts[file.name] ?: error("No text by \"${file.name}\"!")
            Assertions.assertEquals(expected, file.readText())
        }
    }
}
