package sp.gx.core

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.xml.sax.InputSource
import sp.gx.core.util.element
import sp.gx.core.util.single
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

internal class MavenTest {
    @Test
    fun pomTest() {
        val actual = Maven.pom(
            groupId = "foo",
            artifactId = "bar",
            version = "42",
            packaging = "baz",
        )
        val expected = """
            <project xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd"
             xmlns="http://maven.apache.org/POM/4.0.0"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <modelVersion>4.0.0</modelVersion>
            <groupId>foo</groupId>
            <artifactId>bar</artifactId>
            <version>42</version>
            <packaging>baz</packaging>
            </project>
        """.trimIndent().replace("\n", "")
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun pomErrorTest() {
        Assertions.assertThrows(IllegalStateException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.pom(
                modelVersion = "",
                groupId = "",
                artifactId = "",
                version = "",
                packaging = "",
            )
        }
        Assertions.assertThrows(IllegalStateException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.pom(
                modelVersion = "0",
                groupId = "",
                artifactId = "",
                version = "",
                packaging = "",
            )
        }
        Assertions.assertThrows(IllegalStateException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.pom(
                modelVersion = "0",
                groupId = "foo",
                artifactId = "",
                version = "",
                packaging = "",
            )
        }
        Assertions.assertThrows(IllegalStateException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.pom(
                modelVersion = "0",
                groupId = "foo",
                artifactId = "bar",
                version = "",
                packaging = "",
            )
        }
        Assertions.assertThrows(IllegalStateException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.pom(
                modelVersion = "0",
                groupId = "foo",
                artifactId = "bar",
                version = "42",
                packaging = "",
            )
        }
    }

    @Test
    fun metadataTest() {
        val actual = Maven.metadata(
            groupId = "foo",
            artifactId = "bar",
            version = "42",
        )
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        val document = builder.parse(InputSource(StringReader(actual)))
        val root = document.documentElement
        Assertions.assertEquals("metadata", root.tagName)
        Assertions.assertEquals("foo", root.single("groupId").textContent)
        Assertions.assertEquals("bar", root.single("artifactId").textContent)
        root.single("versioning").element().also { versioning ->
            Assertions.assertEquals("42", versioning.single("versions").element().single("version").textContent)
            Assertions.assertFalse(versioning.single("lastUpdated").textContent.isEmpty())
        }
    }

    @Test
    fun metadataErrorTest() {
        Assertions.assertThrows(IllegalStateException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.metadata(
                groupId = "",
                artifactId = "",
                version = "",
            )
        }
        Assertions.assertThrows(IllegalStateException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.metadata(
                groupId = "foo",
                artifactId = "",
                version = "",
            )
        }
        Assertions.assertThrows(IllegalStateException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.metadata(
                groupId = "foo",
                artifactId = "bar",
                version = "",
            )
        }
    }
}
