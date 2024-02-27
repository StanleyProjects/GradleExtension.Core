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
     * @throws IllegalArgumentException if [owner] is blank.
     * @throws IllegalArgumentException if [name] is blank.
     * @author [Stanley Wintergreen](https://github.com/kepocnhh)
     * @since 0.5.0
     */
    @Suppress("StringLiteralDuplication")
    class Repository(val owner: String, val name: String) {
        init {
            require(owner.isNotBlank()) { "The owner is blank!" }
            require(name.isNotBlank()) { "The name is blank!" }
        }

        /**
         * Usage:
         * ```
         * val repository = GitHub.Repository(owner = "foo", name = "bar")
         * val expected = GitHub.url(owner = repository.owner, name = repository.name)
         * assertEquals(expected, repository.url())
         * ```
         * @return The [URL] of this [GitHub.Repository].
         * @author [Stanley Wintergreen](https://github.com/kepocnhh)
         * @since 0.4.2
         */
        fun url(): URL {
            return url(owner = owner, name = name)
        }

        /**
         * Usage:
         * ```
         * val repository = GitHub.Repository(owner = "foo", name = "bar")
         * val expected = GitHub.pages(owner = repository.owner, name = repository.name)
         * assertEquals(expected, repository.pages())
         * ```
         * @return The [URL] from the GitHub [pages](https://pages.github.com) of this [GitHub.Repository].
         * @author [Stanley Wintergreen](https://github.com/kepocnhh)
         * @since 0.4.2
         */
        fun pages(): URL {
            return pages(owner = owner, name = name)
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
     * @throws IllegalArgumentException if [owner] is blank.
     * @throws IllegalArgumentException if [name] is blank.
     * @author [Stanley Wintergreen](https://github.com/kepocnhh)
     * @since 0.5.0
     */
    @Suppress("StringLiteralDuplication")
    fun pages(
        owner: String,
        name: String,
    ): URL {
        require(owner.isNotBlank()) { "The owner is blank!" }
        require(name.isNotBlank()) { "The name is blank!" }
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
     * @throws IllegalArgumentException if [owner] is blank.
     * @throws IllegalArgumentException if [name] is blank.
     * @author [Stanley Wintergreen](https://github.com/kepocnhh)
     * @since 0.2.4
     */
    @Suppress("StringLiteralDuplication")
    fun url(
        owner: String,
        name: String,
    ): URL {
        require(owner.isNotBlank()) { "The owner is blank!" }
        require(name.isNotBlank()) { "The name is blank!" }
        val spec = StringBuilder("https://github.com")
            .append("/")
            .append(owner)
            .append("/")
            .append(name)
            .toString()
        return URL(spec)
    }
}
