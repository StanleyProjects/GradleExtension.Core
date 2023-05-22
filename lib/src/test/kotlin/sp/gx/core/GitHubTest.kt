package sp.gx.core

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.net.URL

internal class GitHubTest {
    @Test
    fun pagesTest() {
        val actual = GitHub.pages(
            owner = "foo",
            name = "bar",
        )
        val expected = URL("https://foo.github.io/bar")
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun pagesErrorTest() {
        Assertions.assertThrows(IllegalStateException::class.java) {
            @Suppress("IgnoredReturnValue")
            GitHub.pages(owner = "", name = "")
        }
        Assertions.assertThrows(IllegalStateException::class.java) {
            @Suppress("IgnoredReturnValue")
            GitHub.pages(owner = "foo", name = "")
        }
    }

    @Test
    fun urlTest() {
        val actual = GitHub.url(
            owner = "foo",
            name = "bar",
        )
        val expected = URL("https://github.com/foo/bar")
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun urlErrorTest() {
        Assertions.assertThrows(IllegalStateException::class.java) {
            @Suppress("IgnoredReturnValue")
            GitHub.url(owner = "", name = "")
        }
        Assertions.assertThrows(IllegalStateException::class.java) {
            @Suppress("IgnoredReturnValue")
            GitHub.url(owner = "foo", name = "")
        }
    }
}
