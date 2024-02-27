package sp.gx.core

import java.net.URL

/**
 * Usage:
 * ```
 * val url = URL("https://github.com").resolve("foo", "", "bar", " ", "baz")
 * assertEquals(URL("https://github.com/foo/bar/baz"), url)
 * ```
 * @return concatenated [this], first [segment] and all the not blank [other] segments separated using "/".
 * @throws IllegalArgumentException if [segment] is blank.
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.5.0
 */
fun URL.resolve(segment: String, vararg other: String): URL {
    require(segment.isNotBlank()) { "The first segment is blank!" }
    val builder = StringBuilder(toString())
        .append("/")
        .append(segment)
    for (it in other) {
        if (it.isNotBlank()) {
            builder.append("/")
                .append(it)
        }
    }
    return URL(builder.toString())
}
