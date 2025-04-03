package sp.gx.core

import groovy.namespace.QName
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.nio.file.Files

internal class XMLUtilTest {
    @Test
    fun xmlTest() {
        val projectDir = Files.createTempDirectory("unittest").toFile()
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        projectDir.resolve("bar").writeText(xml)
        val actual = project.layout.projectDirectory.xml("bar").getAt(QName.valueOf("uses-permission")).map {
            check(it is groovy.util.Node)
            val key = QName.valueOf("{http://schemas.android.com/apk/res/android}name")
            it.attribute(key) as String
        }.toSortedSet()
        assertEquals(expected, actual)
    }

    @Test
    fun qnTest() {
        val projectDir = Files.createTempDirectory("unittest").toFile()
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        projectDir.resolve("bar").writeText(xml)
        val actual = project.layout.projectDirectory.xml("bar").getAt("uses-permission".qn()).map {
            check(it is groovy.util.Node)
            val key = "{http://schemas.android.com/apk/res/android}name".qn()
            it.attribute(key) as String
        }.toSortedSet()
        assertEquals(expected, actual)
    }

    @Test
    fun stringTest() {
        val projectDir = Files.createTempDirectory("unittest").toFile()
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        projectDir.resolve("bar").writeText(xml)
        val actual = project.layout.projectDirectory.xml("bar").getAt("uses-permission".qn()).map {
            check(it is groovy.util.Node)
            val key = "{http://schemas.android.com/apk/res/android}name".qn()
            it.string(key)
        }.toSortedSet()
        assertEquals(expected, actual)
    }

    @Test
    fun stringNotFoundTest() {
        val projectDir = Files.createTempDirectory("unittest").toFile()
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        val xml = """
            <manifest xmlns:android="http://schemas.android.com/apk/res/android">
            <uses-permission android:foo="abcd_01"/>
            <uses-permission android:foo="abcd_02"/>
            </manifest>
        """.trimIndent()
        projectDir.resolve("bar").writeText(xml)
        project.layout.projectDirectory.xml("bar").getAt("uses-permission".qn()).forEach {
            check(it is groovy.util.Node)
            val key = "{http://schemas.android.com/apk/res/android}name".qn()
            val error = assertThrows(IllegalStateException::class.java) {
                it.string(key)
            }
            assertEquals("Attribute by key \"$key\" does not exist!", error.message)
        }
    }

    @Test
    fun mapTest() {
        val projectDir = Files.createTempDirectory("unittest").toFile()
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        projectDir.resolve("bar").writeText(xml)
        val actual = project.layout.projectDirectory.xml("bar").map("uses-permission".qn()) {
            it.string("{http://schemas.android.com/apk/res/android}name".qn())
        }.toSortedSet()
        assertEquals(expected, actual)
    }

    @Test
    fun mapEmptyTest() {
        val projectDir = Files.createTempDirectory("unittest").toFile()
        val project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        val xml = """
            <manifest xmlns:android="http://schemas.android.com/apk/res/android">
            </manifest>
        """.trimIndent()
        projectDir.resolve("bar").writeText(xml)
        val actual = project.layout.projectDirectory.xml("bar").map("uses-permission".qn()) {
            error("Impossible!")
        }
        assertTrue(actual.isEmpty())
    }

    companion object {
        private const val xml = """
            <manifest xmlns:android="http://schemas.android.com/apk/res/android">
            <uses-permission android:name="abcd_01"/>
            <uses-permission android:name="abcd_02"/>
            </manifest>
        """
        private val expected = sortedSetOf(
            "abcd_01",
            "abcd_02",
        )
    }
}
