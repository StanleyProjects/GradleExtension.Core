package sp.gx.core

import java.net.URL

/**
 * A set of functions for working with [Markdown](https://en.wikipedia.org/wiki/Markdown) markup language.
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.2.2
 */
object Markdown {
    /**
     * @return a URL with a description in [link](https://docs.github.com/en/get-started/writing-on-github/getting-started-with-writing-and-formatting-on-github/basic-writing-and-formatting-syntax#links) format.
     * @param text The description.
     * @param url The value of the link.
     * @throws IllegalArgumentException if [text] is blank.
     * @author [Stanley Wintergreen](https://github.com/kepocnhh)
     * @since 0.5.1
     */
    fun link(
        text: String,
        url: URL,
    ): String {
        require(text.isNotBlank()) { "The text is blank!" }
        return "[$text]($url)"
    }

    /**
     * @return a URL with a description in [image](https://docs.github.com/en/get-started/writing-on-github/getting-started-with-writing-and-formatting-on-github/basic-writing-and-formatting-syntax#images) format.
     * @param text The description.
     * @param url The value of the link.
     * @author [Stanley Wintergreen](https://github.com/kepocnhh)
     * @since 0.2.2
     */
    fun image(
        text: String,
        url: URL,
    ): String {
        return "!" + link(text = text, url = url)
    }
}
