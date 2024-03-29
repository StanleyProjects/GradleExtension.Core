package sp.gx.core

import org.gradle.api.DefaultTask
import org.gradle.internal.FileUtils
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

internal class TaskUtilTest {
    @Test
    @Suppress("StringLiteralDuplication")
    fun taskTest() {
        val projectDir = Files.createTempDirectory("TaskUtilTest:taskTest").toFile().let {
            FileUtils.canonicalize(it)
        }
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        val task = project.task<DefaultTask>("foo", "bar", "", "baz", " ", "qux")
        Assertions.assertEquals("fooBarBazQux", task.name)
    }

    @Test
    @Suppress("StringLiteralDuplication")
    fun taskBlockTest() {
        val projectDir = Files.createTempDirectory("TaskUtilTest:taskBlockTest").toFile().let {
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

    @Test
    @Suppress("StringLiteralDuplication")
    fun taskWithNoTypeTest() {
        val projectDir = Files.createTempDirectory("TaskUtilTest:taskWithNoTypeTest").toFile().let {
            FileUtils.canonicalize(it)
        }
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        val task = project.tasks.create("foo", "bar", "", "baz", " ", "qux")
        Assertions.assertEquals("fooBarBazQux", task.name)
    }

    @Test
    @Suppress("StringLiteralDuplication")
    fun taskWithNoTypeBlockTest() {
        val projectDir = Files.createTempDirectory("TaskUtilTest:taskWithNoTypeBlockTest").toFile().let {
            FileUtils.canonicalize(it)
        }
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        val taskBuild = AtomicBoolean(false)
        Assertions.assertFalse(taskBuild.get())
        val task = project.tasks.create("foo", "bar", "", "baz", " ", "qux") {
            taskBuild.set(true)
        }
        Assertions.assertEquals("fooBarBazQux", task.name)
        Assertions.assertTrue(taskBuild.get())
    }

    @Test
    @Suppress("StringLiteralDuplication", "IgnoredReturnValue")
    fun getByNameTest() {
        val projectDir = Files.createTempDirectory("TaskUtilTest:getByNameTest").toFile().let {
            FileUtils.canonicalize(it)
        }
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        project.tasks.create("foo", "bar", "", "baz", " ", "qux")
        val task = project.tasks.getByName<DefaultTask>("foo", "bar", "", "baz", " ", "qux")
        Assertions.assertEquals("fooBarBazQux", task.name)
    }

    @Test
    @Suppress("StringLiteralDuplication", "IgnoredReturnValue")
    fun getByNameBlockTest() {
        val projectDir = Files.createTempDirectory("TaskUtilTest:getByNameBlockTest").toFile().let {
            FileUtils.canonicalize(it)
        }
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        val counter = AtomicInteger(0)
        Assertions.assertEquals(0, counter.get())
        project.task<DefaultTask>("foo", "bar", "", "baz", " ", "qux") {
            counter.incrementAndGet()
        }
        val task = project.tasks.getByName<DefaultTask>("foo", "bar", "", "baz", " ", "qux") {
            counter.incrementAndGet()
        }
        Assertions.assertEquals("fooBarBazQux", task.name)
        Assertions.assertEquals(2, counter.get())
    }
}
