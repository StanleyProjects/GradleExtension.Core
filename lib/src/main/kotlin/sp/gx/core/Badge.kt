package sp.gx.core

import java.net.URL

object Badge {
    fun url(
        label: String,
        message: String,
        labelColor: String = "212121",
        color: String,
        style: String = "flat",
    ): URL {
        check(label.isNotEmpty())
        check(message.isNotEmpty())
        check(labelColor.isNotEmpty())
        check(color.isNotEmpty())
        check(style.isNotEmpty())
        val host = "https://img.shields.io"
        val spec = mapOf(
            "label" to label,
            "message" to message,
            "labelColor" to labelColor,
            "color" to color,
            "style" to style,
        ).toList().joinToString(
            prefix = "$host/static/v1?",
            separator = "&",
        ) { (key, value) ->
            "$key=$value"
        }
        return URL(spec)
    }
}
