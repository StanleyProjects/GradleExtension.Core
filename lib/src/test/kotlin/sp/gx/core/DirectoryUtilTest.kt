package sp.gx.core

import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.nio.file.Files

internal class DirectoryUtilTest {
    @Test
    fun fooTest() {
        val projectDir = Files.createTempDirectory("unittest").toFile()
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        check(projectDir.resolve("foo").mkdirs())
        val expected = "foobarbaz"
        projectDir.resolve("foo/bar").writeText(expected)
        val dir = project.layout.projectDirectory.dir("foo")
        Assertions.assertEquals(expected, dir.asFile("bar").readText())
    }
}
