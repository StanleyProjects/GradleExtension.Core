package sp.gx.core

import java.net.URL

object Markdown {
    fun url(
        text: String,
        value: URL,
    ): String {
        check(text.isNotEmpty())
        return "[$text]($value)"
    }

    fun image(
        text: String,
        path: URL,
    ): String {
        return "!" + url(text = text, value = path)
    }
}
