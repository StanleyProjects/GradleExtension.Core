package sp.gx.core

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

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
        Assertions.assertThrows(IllegalStateException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.Artifact(group = "", id = "")
        }
        Assertions.assertThrows(IllegalStateException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.Artifact(group = "foo", id = "")
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
}
