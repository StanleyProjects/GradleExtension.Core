package sp.gx.core

import org.gradle.api.DefaultTask
import org.gradle.internal.FileUtils
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.util.concurrent.atomic.AtomicBoolean

internal class TaskUtilTest {
    @Test
    fun taskTest() {
        val projectDir = Files.createTempDirectory("unittest").toFile().let {
            FileUtils.canonicalize(it)
        }
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        val task = project.task<DefaultTask>("foo", "bar", "", "baz", " ", "qux")
        Assertions.assertEquals("fooBarBazQux", task.name)
    }

    @Test
    fun taskTypeTest() {
        val projectDir = Files.createTempDirectory("unittest").toFile().let {
            FileUtils.canonicalize(it)
        }
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        val taskBuild = AtomicBoolean(false)
        Assertions.assertFalse(taskBuild.get())
        val task = project.task<DefaultTask>("foo", "bar", "", "baz", " ", "qux") {
            taskBuild.set(true)
        }
        Assertions.assertEquals("fooBarBazQux", task.name)
        Assertions.assertTrue(taskBuild.get())
    }
}
