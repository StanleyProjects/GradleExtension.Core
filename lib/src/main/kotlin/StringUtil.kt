fun String.task(vararg postfix: String): String {
    check(isNotEmpty())
    return postfix
        .filter { it.isNotEmpty() }
        .joinToString(separator = "", prefix = this) { it.capitalize() }
}

fun String.version(vararg postfix: String): String {
    check(isNotEmpty())
    return postfix
        .filter { it.isNotEmpty() }
        .joinToString(separator = "-", prefix = this)
}
