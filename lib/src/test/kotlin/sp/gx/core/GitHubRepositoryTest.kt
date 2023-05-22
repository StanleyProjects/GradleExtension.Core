package sp.gx.core

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class GitHubRepositoryTest {
    @Test
    fun constructorTest() {
        val owner = "foo"
        val name = "bar"
        val repository = GitHub.Repository(owner = owner, name = name)
        Assertions.assertEquals(owner, repository.owner)
        Assertions.assertEquals(name, repository.name)
    }

    @Test
    fun constructorErrorTest() {
        Assertions.assertThrows(IllegalStateException::class.java) {
            GitHub.Repository(owner = "", name = "")
        }
        Assertions.assertThrows(IllegalStateException::class.java) {
            GitHub.Repository(owner = "foo", name = "")
        }
    }

    @Test
    fun toStringTest() {
        val repository = GitHub.Repository(owner = "foo", name = "bar")
        Assertions.assertEquals("Repository(foo/bar)", repository.toString())
    }

    @Test
    fun hashCodeTest() {
        GitHub.Repository(owner = "foo", name = "bar").hashCode()
    }

    @Test
    fun equalsTest() {
        val owner = "foo"
        val name = "bar"
        val first = GitHub.Repository(owner = owner, name = name)
        val second = GitHub.Repository(owner = owner, name = name)
        Assertions.assertEquals(first, second)
    }

    @Test
    fun equalsNotTest() {
        val repository = GitHub.Repository(owner = "foo", name = "bar")
        Assertions.assertNotEquals(repository, GitHub.Repository(owner = "42", name = "bar"))
        Assertions.assertNotEquals(repository, GitHub.Repository(owner = "foo", name = "42"))
        Assertions.assertFalse(repository.equals(Unit))
    }
}
