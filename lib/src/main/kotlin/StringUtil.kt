@file:Suppress("MissingPackageDeclaration")

import java.lang.StringBuilder
import java.util.Locale

fun String.uppercaseFirstChar(): String {
    return replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}

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
