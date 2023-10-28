package sp.gx.core

import java.lang.StringBuilder
import java.util.Locale

/**
 * Usage:
 * ```
 * val cased = "foo".uppercaseFirstChar()
 * assertEquals("Foo", cased)
 * ```
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
 * Usage:
 * ```
 * val cased = camelCase("foo", "bar", "baz")
 * assertEquals("fooBarBaz", cased)
 * ```
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
 * Usage:
 * ```
 * val version = kebabCase("0.1", "bar", "baz")
 * assertEquals("0.1-bar-baz", version)
 * ```
 * @return The string from first [segment] and all the not empty [other] segments separated using "-".
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
 * Usage:
 * ```
 * val text = "foo".filled()
 * assertTrue(text.isNotEmpty())
 * ```
 * @return [this] receiver string.
 * @throws IllegalStateException if [this] receiver [String] is empty.
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.6
 */
fun String.filled(): String {
    check(isNotEmpty())
    return this
}

/**
 * Usage:
 * ```
 * val cased = colonCase("foo", "bar", "baz")
 * assertEquals("foo:bar:baz", cased)
 * ```
 * @return The string from first [segment] and all the not empty [other] segments separated using ":".
 * @throws IllegalStateException if [segment] is empty.
 * @see [Iterable.joinToString]
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.3.0
 */
fun colonCase(segment: String, vararg other: String): String {
    check(segment.isNotEmpty())
    val builder = StringBuilder(segment)
    for (it in other) {
        if (it.isNotEmpty()) {
            builder.append(":")
                .append(it)
        }
    }
    return builder.toString()
}

/**
 * Usage:
 * ```
 * val cased = slashCase("foo", "bar", "baz")
 * assertEquals("foo/bar/baz", cased)
 * ```
 * @return The string from first [segment] and all the not empty [other] segments separated using "/".
 * @throws IllegalStateException if [segment] is empty.
 * @see [Iterable.joinToString]
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.3.0
 */
fun slashCase(segment: String, vararg other: String): String {
    check(segment.isNotEmpty())
    val builder = StringBuilder(segment)
    for (it in other) {
        if (it.isNotEmpty()) {
            builder.append("/")
                .append(it)
        }
    }
    return builder.toString()
}
