package sp.gx.core

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.net.URL

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
            @Suppress("IgnoredReturnValue")
            GitHub.Repository(owner = "", name = "")
        }
        Assertions.assertThrows(IllegalStateException::class.java) {
            @Suppress("IgnoredReturnValue")
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
        @Suppress("IgnoredReturnValue")
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

    @Test
    fun pagesTest() {
        val actual =
            GitHub.Repository(
                owner = "foo",
                name = "bar",
            ).pages()
        val expected = URL("https://foo.github.io/bar")
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun urlTest() {
        val actual =
            GitHub.Repository(
                owner = "foo",
                name = "bar",
            ).url()
        val expected = URL("https://github.com/foo/bar")
        Assertions.assertEquals(expected, actual)
    }
}
