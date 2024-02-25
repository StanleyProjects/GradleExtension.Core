package sp.gx.core

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.net.URL

internal class MavenArtifactTest {
    @Test
    fun constructorTest() {
        val groupId = "foo"
        val artifactId = "bar"
        val artifact = Maven.Artifact(group = groupId, id = artifactId)
        Assertions.assertEquals(groupId, artifact.group)
        Assertions.assertEquals(artifactId, artifact.id)
    }

    @Test
    fun constructorErrorTest() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.Artifact(group = "", id = "")
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.Artifact(group = " ", id = "")
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.Artifact(group = "foo", id = "")
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.Artifact(group = "foo", id = " ")
        }
    }

    @Test
    fun toStringTest() {
        val groupId = "foo"
        val artifactId = "bar"
        val artifact = Maven.Artifact(group = groupId, id = artifactId)
        Assertions.assertEquals("Artifact($groupId/$artifactId)", artifact.toString())
    }

    @Test
    fun hashCodeTest() {
        val groupId = "foo"
        val artifactId = "bar"
        val artifact = Maven.Artifact(group = groupId, id = artifactId)
        @Suppress("IgnoredReturnValue")
        artifact.hashCode()
    }

    @Test
    fun equalsTest() {
        val groupId = "foo"
        val artifactId = "bar"
        val first = Maven.Artifact(group = groupId, id = artifactId)
        val second = Maven.Artifact(group = groupId, id = artifactId)
        Assertions.assertTrue(first == second)
    }

    @Test
    fun equalsNotTest() {
        Assertions.assertFalse(Maven.Artifact(group = "foo", id = "42") == Maven.Artifact(group = "bar", id = "42"))
        Assertions.assertFalse(Maven.Artifact(group = "42", id = "foo") == Maven.Artifact(group = "42", id = "bar"))
        Assertions.assertFalse(Maven.Artifact(group = "foo", id = "42").equals(Unit))
    }

    @Test
    fun snapshotUrlTest() {
        val artifact = Maven.Artifact(group = "foo", id = "bar")
        val actual = Maven.Snapshot.url(
            artifact = artifact,
            version = "42",
        )
        val expected = URL("https://s01.oss.sonatype.org/content/repositories/snapshots/foo/bar/42")
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun pomTest() {
        val artifact = Maven.Artifact(group = "foo", id = "bar")
        val actual = artifact.pom(
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
}
