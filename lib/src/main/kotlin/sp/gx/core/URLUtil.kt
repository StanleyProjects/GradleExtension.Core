package sp.gx.core

import java.net.URL

/**
 * Usage:
 * ```
 * val url = URL("https://github.com").resolve("foo")
 * assertEquals(URL("https://github.com/foo"), url)
 * ```
 * @return concatenated [this] and [relative] paths.
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.3.0
 */
fun URL.resolve(relative: String): URL {
    val spec = StringBuilder(toString())
        .append("/")
        .append(relative)
        .toString()
    return URL(spec)
}
