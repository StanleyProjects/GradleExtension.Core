package sp.gx.core

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

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
    @Suppress("LongMethod")
    fun pomErrorTest() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.pom(
                modelVersion = "",
                groupId = "",
                artifactId = "",
                version = "",
                packaging = "",
            )
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.pom(
                modelVersion = " ",
                groupId = "",
                artifactId = "",
                version = "",
                packaging = "",
            )
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.pom(
                modelVersion = "0",
                groupId = "",
                artifactId = "",
                version = "",
                packaging = "",
            )
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.pom(
                modelVersion = "0",
                groupId = " ",
                artifactId = "",
                version = "",
                packaging = "",
            )
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.pom(
                modelVersion = "0",
                groupId = "foo",
                artifactId = "",
                version = "",
                packaging = "",
            )
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.pom(
                modelVersion = "0",
                groupId = "foo",
                artifactId = " ",
                version = "",
                packaging = "",
            )
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.pom(
                modelVersion = "0",
                groupId = "foo",
                artifactId = "bar",
                version = "",
                packaging = "",
            )
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.pom(
                modelVersion = "0",
                groupId = "foo",
                artifactId = "bar",
                version = " ",
                packaging = "",
            )
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.pom(
                modelVersion = "0",
                groupId = "foo",
                artifactId = "bar",
                version = "42",
                packaging = "",
            )
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.pom(
                modelVersion = "0",
                groupId = "foo",
                artifactId = "bar",
                version = "42",
                packaging = " ",
            )
        }
    }
}
