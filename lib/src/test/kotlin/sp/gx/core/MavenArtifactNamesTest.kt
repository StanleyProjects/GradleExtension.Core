package sp.gx.core

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class MavenArtifactNamesTest {
    @Test
    fun moduleNameTest() {
        val groupId = "foo"
        val artifactId = "bar"
        val artifact = Maven.Artifact(group = groupId, id = artifactId)
        Assertions.assertEquals("$groupId:$artifactId", artifact.moduleName())
    }

    @Test
    fun moduleNameVersionTest() {
        val groupId = "foo"
        val artifactId = "bar"
        val version = "qux"
        val artifact = Maven.Artifact(group = groupId, id = artifactId)
        Assertions.assertEquals("$groupId:$artifactId:$version", artifact.moduleName(version = version))
    }

    @Test
    fun moduleNameVersionErrorTest() {
        val groupId = "foo"
        val artifactId = "bar"
        val artifact = Maven.Artifact(group = groupId, id = artifactId)
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            artifact.moduleName(version = "")
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            artifact.moduleName(version = " ")
        }
    }

    @Test
    fun nameTest() {
        val groupId = "foo"
        val artifactId = "bar"
        val version = "qux"
        val artifact = Maven.Artifact(group = groupId, id = artifactId)
        Assertions.assertEquals("$artifactId-$version", artifact.name(version = version))
    }

    @Test
    fun nameErrorTest() {
        val groupId = "foo"
        val artifactId = "bar"
        val artifact = Maven.Artifact(group = groupId, id = artifactId)
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            artifact.name(version = "")
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            artifact.name(version = " ")
        }
    }
}
