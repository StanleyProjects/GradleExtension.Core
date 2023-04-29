import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import java.io.File
import org.gradle.testfixtures.ProjectBuilder

internal class ProjectUtilTest {
    @Test
    fun buildDirTest() {
        val project = ProjectBuilder.builder().build()
        val issuer = "foo"
        val actual = project.buildDir(issuer)
        val expected = File(project.buildDir, issuer)
        assertEquals(expected, actual)
    }

    @Test
    fun rootDirTest() {
        val project = ProjectBuilder.builder().build()
        val issuer = "foo"
        val actual = project.rootDir(issuer)
        val expected = File(project.rootDir, issuer)
        assertEquals(expected, actual)
    }

    @Test
    fun buildSrcTest() {
        val project = ProjectBuilder.builder().build()
        val issuer = "foo"
        val actual = project.buildSrc(issuer)
        val expected = project.rootDir.resolve("buildSrc").resolve(issuer)
        assertEquals(expected, actual)
    }
}
