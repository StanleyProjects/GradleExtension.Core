package sp.gx.core

import java.lang.StringBuilder
import java.util.Locale

/**
 * @return The copy of this string having its first letter titlecased using the rules of the default locale,
 * or the original string if it's empty or already starts with a title case letter.
 * @see [String.capitalize]
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.5
 */
fun String.uppercaseFirstChar(): String {
    return replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}

/**
 * @return The string from first [segment] and all the not empty capitalized [other] segments.
 * @throws IllegalStateException if [segment] is empty.
 * @see [Iterable.joinToString]
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.1.1
 */
fun camelCase(segment: String, vararg other: String): String {
    check(segment.isNotEmpty())
    val builder = StringBuilder(segment)
    for (it in other) {
        if (it.isNotEmpty()) {
            builder.append(it.uppercaseFirstChar())
        }
    }
    return builder.toString()
}

/**
 * @return The string from all the not empty segments separated using "-".
 * @throws IllegalStateException if [segment] is empty.
 * @see [Iterable.joinToString]
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.1.1
 */
fun kebabCase(segment: String, vararg other: String): String {
    check(segment.isNotEmpty())
    val builder = StringBuilder(segment)
    for (it in other) {
        if (it.isNotEmpty()) {
            builder.append("-")
                .append(it)
        }
    }
    return builder.toString()
}

/**
 * @return [this] receiver string.
 * @throws IllegalStateException if [this] receiver [String] is empty.
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.6
 */
fun String.filled(): String {
    check(isNotEmpty())
    return this
}
