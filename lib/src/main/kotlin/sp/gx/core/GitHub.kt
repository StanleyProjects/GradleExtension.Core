package sp.gx.core

import java.net.URL
import java.util.Objects

/**
 * A set of functions for working with [GitHub](https://github.com).
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.2.4
 */
object GitHub {
    /**
     * Encapsulates data about a GitHub [repository](https://docs.github.com/en/rest/repos).
     * @property owner The account owner of the GitHub repository.
     * @property name The name of the GitHub repository.
     * @throws IllegalStateException if [owner] is empty.
     * @throws IllegalStateException if [name] is empty.
     * @author [Stanley Wintergreen](https://github.com/kepocnhh)
     * @since 0.2.4
     */
    class Repository(val owner: String, val name: String) {
        init {
            check(owner.isNotEmpty())
            check(name.isNotEmpty())
        }

        override fun toString(): String {
            return "Repository($owner/$name)"
        }

        override fun hashCode(): Int {
            return Objects.hash(owner, name)
        }

        override fun equals(other: Any?): Boolean {
            if (other !is Repository) return false
            return owner == other.owner && name == other.name
        }
    }

    /**
     * Usage:
     * ```
     * val url = GitHub.pages(owner = "foo", name = "bar")
     * assertEquals(cURL.get(url).code, 200)
     * ```
     * @return The [URL] from the GitHub [pages](https://pages.github.com) of the repository.
     * @throws IllegalStateException if [owner] is empty.
     * @throws IllegalStateException if [name] is empty.
     * @author [Stanley Wintergreen](https://github.com/kepocnhh)
     * @since 0.2.4
     */
    fun pages(
        owner: String,
        name: String,
    ): URL {
        check(owner.isNotEmpty())
        check(name.isNotEmpty())
        val spec = StringBuilder("https://$owner.github.io")
            .append("/")
            .append(name)
            .toString()
        return URL(spec)
    }

    /**
     * Usage:
     * ```
     * val url = GitHub.url(owner = "foo", name = "bar")
     * assertEquals(cURL.get(url).code, 200)
     * ```
     * @return The [URL] of the GitHub repository.
     * @throws IllegalStateException if [owner] is empty.
     * @throws IllegalStateException if [name] is empty.
     * @author [Stanley Wintergreen](https://github.com/kepocnhh)
     * @since 0.2.4
     */
    fun url(
        owner: String,
        name: String,
    ): URL {
        check(owner.isNotEmpty())
        check(name.isNotEmpty())
        val spec = StringBuilder("https://github.com")
            .append("/")
            .append(owner)
            .append("/")
            .append(name)
            .toString()
        return URL(spec)
    }
}
