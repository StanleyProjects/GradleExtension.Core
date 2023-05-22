package sp.gx.core

import java.net.URI
import java.net.URL
import java.util.Objects

object GitHub {
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

    fun pages(
        owner: String,
        name: String,
    ): URL {
        check(owner.isNotEmpty())
        check(name.isNotEmpty())
        return URI("https","$owner.github.io", null, null)
            .resolve(name)
            .toURL()
    }

    fun url(
        owner: String,
        name: String,
    ): URL {
        check(owner.isNotEmpty())
        check(name.isNotEmpty())
        return URI("https","github.com", null, null)
            .resolve(owner)
            .resolve(name)
            .toURL()
    }
}
