package sp.gx.core

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.net.URL

internal class MarkdownTest {
    @Test
    fun urlTest() {
        val actual = Markdown.link(
            text = "foo",
            url = URL("https://github.com/foo"),
        )
        Assertions.assertEquals("[foo](https://github.com/foo)", actual)
    }

    @Test
    fun urlErrorTest() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Markdown.link(
                text = "",
                url = URL("https://github.com/bar"),
            )
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Markdown.link(
                text = " ",
                url = URL("https://github.com/bar"),
            )
        }
    }

    @Test
    fun imageTest() {
        val actual = Markdown.image(
            text = "foo",
            url = URL("https://github.com/baz"),
        )
        Assertions.assertEquals("![foo](https://github.com/baz)", actual)
    }
}
