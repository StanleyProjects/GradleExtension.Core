import java.lang.StringBuilder

fun String.task(segment: String, vararg other: String): String {
    check(isNotEmpty())
    val builder = StringBuilder(this)
    if (segment.isNotEmpty()) {
        builder.append(segment.capitalize())
    }
    if (other.isEmpty()) {
        return builder.toString()
    }
    val postfix = other
        .filter { it.isNotEmpty() }
        .joinToString(separator = "") { it.capitalize() }
    if (postfix.isEmpty()) {
        return builder.toString()
    }
    builder.append(postfix)
    return builder.toString()
}

fun String.version(segment: String, vararg other: String): String {
    check(isNotEmpty())
    val builder = StringBuilder(this)
    if (segment.isNotEmpty()) {
        builder.append("-")
        builder.append(segment)
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
    builder.append("-")
    builder.append(postfix)
    return builder.toString()
}
