package sp.gx.core

import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
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
            if (other !is Artifact) return false
            return group == other.group && id == other.id
        }
    }

    fun pom(
        modelVersion: String = "4.0.0",
        groupId: String,
        artifactId: String,
        version: String,
        packaging: String,
    ): String {
        check(modelVersion.isNotEmpty())
        check(groupId.isNotEmpty())
        check(artifactId.isNotEmpty())
        check(version.isNotEmpty())
        check(packaging.isNotEmpty())
        val host = "http://maven.apache.org"
        val url = "$host/POM/$modelVersion"
        val project = setOf(
            "xsi:schemaLocation" to "$url $host/xsd/maven-$modelVersion.xsd",
            "xmlns" to url,
            "xmlns:xsi" to "http://www.w3.org/2001/XMLSchema-instance",
        ).joinToString(separator = " ") { (key, value) ->
            "$key=\"$value\""
        }
        return setOf(
            "modelVersion" to modelVersion,
            "groupId" to groupId,
            "artifactId" to artifactId,
            "version" to version,
            "packaging" to packaging,
        ).joinToString(
            prefix = "<project $project>",
            separator = "",
            postfix = "</project>",
        ) { (key, value) ->
            "<$key>$value</$key>"
        }
    }

    fun metadata(
        groupId: String,
        artifactId: String,
        version: String,
        dateTime: LocalDateTime = LocalDateTime.now(),
        dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss"),
    ): String {
        check(groupId.isNotEmpty())
        check(artifactId.isNotEmpty())
        check(version.isNotEmpty())
        return """
            <metadata>
                <groupId>$groupId</groupId>
                <artifactId>$artifactId</artifactId>
                <versioning>
                    <versions>
                        <version>$version</version>
                    </versions>
                    <lastUpdated>${dateTimeFormatter.format(dateTime)}</lastUpdated>
                </versioning>
            </metadata>
            """.trimIndent()
    }

    object Snapshot {
        fun url(
            groupId: String,
            artifactId: String,
            version: String,
        ): URL {
            check(groupId.isNotEmpty())
            check(artifactId.isNotEmpty())
            check(version.isNotEmpty())
            val host = "https://s01.oss.sonatype.org"
            val path = "$host/content/repositories/snapshots"
            val spec = "$path/${groupId.replace('.', '/')}/$artifactId/$version"
            return URL(spec)
        }
    }
}
