package sp.gx.core

import java.util.Objects

/**
 * A set of functions and types for working with [Maven](https://maven.apache.org).
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.2.3
 */
object Maven {
    /**
     * Encapsulates data about an [artifact](https://maven.apache.org/repositories/artifacts.html).
     * @property group The artifact group.
     * @property id The artifact id.
     * @throws IllegalStateException if [group] is empty.
     * @throws IllegalStateException if [id] is empty.
     * @author [Stanley Wintergreen](https://github.com/kepocnhh)
     * @since 0.2.3
     */
    class Artifact(val group: String, val id: String) {
        init {
            check(group.isNotEmpty())
            check(id.isNotEmpty())
        }

        override fun toString(): String {
            return "Artifact($group/$id)"
        }

        override fun hashCode(): Int {
            return Objects.hash(group, id)
        }

        override fun equals(other: Any?): Boolean {
            return when (other) {
                is Artifact -> group == other.group && id == other.id
                else -> false
            }
        }
    }
}
