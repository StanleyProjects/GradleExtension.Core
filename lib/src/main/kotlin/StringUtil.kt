@file:Suppress("MissingPackageDeclaration")

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
 * @return The string from all the not empty capitalized segments.
 * @throws IllegalStateException if receiver is empty.
 * @see [Iterable.joinToString]
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.5
 */
fun String.task(segment: String, vararg other: String): String {
    check(isNotEmpty())
    val builder = StringBuilder(this)
    if (segment.isNotEmpty()) {
        builder.append(segment.uppercaseFirstChar())
    }
    if (other.isEmpty()) {
        return builder.toString()
    }
    val postfix = other
        .filter { it.isNotEmpty() }
        .joinToString(separator = "") { it.uppercaseFirstChar() }
    if (postfix.isEmpty()) {
        return builder.toString()
    }
    return builder.append(postfix)
        .toString()
}

/**
 * @return The string from all the not empty segments separated using "-".
 * @throws IllegalStateException if receiver is empty.
 * @see [Iterable.joinToString]
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.5
 */
fun String.version(segment: String, vararg other: String): String {
    check(isNotEmpty())
    val builder = StringBuilder(this)
    if (segment.isNotEmpty()) {
        builder.append("-")
            .append(segment)
    }
    if (other.isEmpty()) {
        return builder.toString()
    }
    val postfix = other
        .filter { it.isNotEmpty() }
        .joinToString(separator = "-")
    if (postfix.isEmpty()) {
        return builder.toString()
    }
    return builder.append("-")
        .append(postfix)
        .toString()
}

/**
 * @return [this] receiver string.
 * @throws IllegalStateException if [this] receiver string is empty.
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.6
 */
fun String.filled(): String {
    check(isNotEmpty())
    return this
}
