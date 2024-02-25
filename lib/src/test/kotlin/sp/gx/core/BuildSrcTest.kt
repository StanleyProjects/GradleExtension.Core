package sp.gx.core

import org.gradle.api.internal.provider.DefaultProvider
import org.gradle.internal.FileUtils
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Files

internal class BuildSrcTest {
    private fun File.buildSrc(): File {
        return resolve("buildSrc")
    }

    @Test
    fun projectDirectoryTest() {
        val projectDir = Files.createTempDirectory("BuildSrcTest:projectDirectoryTest").toFile().let {
            FileUtils.canonicalize(it)
        }
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        val expected = projectDir.buildSrc().absolutePath
        val actual = project
            .buildSrc
            .projectDirectory
            .asFile
            .absolutePath
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun buildDirectoryTest() {
        val projectDir = Files.createTempDirectory("BuildSrcTest:buildDirectoryTest").toFile().let {
            FileUtils.canonicalize(it)
        }
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        val expected = projectDir.resolve("buildSrc/build").absolutePath
        val actual = project
            .buildSrc
            .buildDirectory
            .get()
            .asFile
            .absolutePath
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun fileTest() {
        val projectDir = Files.createTempDirectory("BuildSrcTest:fileTest").toFile().let {
            FileUtils.canonicalize(it)
        }
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        val expected = "foobarbaz"
        projectDir.buildSrc().mkdirs()
        projectDir.buildSrc().resolve("foo").writeText(expected)
        val expectedFile = projectDir.buildSrc().resolve("foo")
        val actualFile = project.buildSrc.file(DefaultProvider { File("foo") }).get().asFile
        Assertions.assertEquals(expectedFile.absolutePath, actualFile.absolutePath)
        Assertions.assertEquals(expected, actualFile.readText())
    }

    @Test
    @Suppress("MaxChainedCallsOnSameLine")
    fun dirTest() {
        val projectDir = Files.createTempDirectory("BuildSrcTest:dirTest").toFile().let {
            FileUtils.canonicalize(it)
        }
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        projectDir.buildSrc().mkdirs()
        val expected = projectDir.resolve("buildSrc/foo/bar").absolutePath
        val provider = DefaultProvider { File("foo/bar") }
        val actual = project
            .buildSrc
            .dir(provider)
            .get()
            .asFile
            .absolutePath
        Assertions.assertEquals(expected, actual)
        Assertions.assertFalse(project.buildSrc.dir(provider).get().asFile.exists())
        Assertions.assertFalse(project.buildSrc.dir(provider).get().asFile.isDirectory)
        projectDir.resolve("buildSrc/foo/bar").mkdirs()
        Assertions.assertTrue(project.buildSrc.dir(provider).get().asFile.exists())
        Assertions.assertTrue(project.buildSrc.dir(provider).get().asFile.isDirectory)
    }

    @Test
    @Suppress("MagicNumber")
    fun filesTest() {
        val projectDir = Files.createTempDirectory("BuildSrcTest:filesTest").toFile().let {
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
        projectDir.buildSrc().mkdirs()
        texts.forEach { (path, text) ->
            projectDir.buildSrc().resolve(path).writeText(text)
        }
        project.buildSrc.files(texts.keys).forEach { file ->
            val expected = texts[file.name] ?: error("No text by \"${file.name}\"!")
            Assertions.assertEquals(expected, file.readText())
        }
    }
}
