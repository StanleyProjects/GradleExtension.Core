import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import util.MockProject
import java.io.File
import org.gradle.testfixtures.ProjectBuilder

internal class ProjectUtilTest {
    @Test
    fun buildDirTest() {
        val project = MockProject()
        val issuer = "foo"
        val actual = project.buildDir(issuer)
        val expected = File(project.buildDir, issuer)
        assertEquals(expected, actual)
    }

    @Test
    fun fooTest() {
        val project = ProjectBuilder.builder().build()
        val issuer = "foo"
        val actual = project.buildDir(issuer)
        val expected = File(project.buildDir, "bar")
        assertEquals(expected, actual)
    }
}
