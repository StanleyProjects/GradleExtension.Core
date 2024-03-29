package sp.gx.core

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.net.URL

@Suppress("StringLiteralDuplication")
internal class URLUtilTest {
    @Test
    fun resolveTest() {
        val actual = URL("http://foo").resolve("bar")
        Assertions.assertEquals(URL("http://foo/bar"), actual)
    }

    @Test
    fun resolveEmptyTest() {
        val actual = URL("http://foo").resolve("bar", "", "baz")
        Assertions.assertEquals(URL("http://foo/bar/baz"), actual)
    }

    @Test
    fun resolveBlankTest() {
        val actual = URL("http://foo").resolve("bar", " ", "baz")
        Assertions.assertEquals(URL("http://foo/bar/baz"), actual)
    }

    @Test
    fun resolveErrorTest() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            URL("http://foo").resolve("")
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            URL("http://foo").resolve(" ")
        }
    }
}
