package sp.gx.core

import java.net.URL

/**
 * A set of functions for working with badges from the [host](https://img.shields.io).
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.2.2
 */
object Badge {
    /**
     * Builds the URL to the SVG badge from the [host](https://img.shields.io).
     *
     * Usage:
     * ```
     * val url = Badge.url(label = "foo", message = "bar", color = "121212")
     * SVGDownloader.download(url)
     * ```
     * @param label Text on the left.
     * @param message Text on the right.
     * @param labelColor HEX or name of color like "white" on the left. Default is "212121".
     * @param color HEX or name of color like "white" on the right.
     * @param style One of "flat", "flat-square", "plastic" etc. Default is "flat".
     * @throws IllegalStateException if [label] is empty.
     * @throws IllegalStateException if [message] is empty.
     * @throws IllegalStateException if [labelColor] is empty.
     * @throws IllegalStateException if [color] is empty.
     * @throws IllegalStateException if [style] is empty.
     * @author [Stanley Wintergreen](https://github.com/kepocnhh)
     * @since 0.2.2
     */
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
