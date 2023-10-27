package sp.gx.core

import java.net.URL

/**
 * Usage:
 * ```
 * val url = URL("https://github.com").resolve("foo", "", "bar")
 * assertEquals(URL("https://github.com/foo/bar"), url)
 * ```
 * @return concatenated [this], first [segment] and all the not empty [other] segments separated using "/".
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.4.3
 */
fun URL.resolve(segment: String, vararg other: String): URL {
    val builder = StringBuilder(toString())
        .append("/")
        .append(segment)
    for (it in other) {
        if (it.isNotEmpty()) {
            builder.append("/")
                .append(it)
        }
    }
    return URL(builder.toString())
}
