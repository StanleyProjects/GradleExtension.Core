package sp.gx.core

import java.net.URL
import java.util.Objects

/**
 * A set of functions and types for working with [Maven](https://maven.apache.org).
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.2.3
 */
@Suppress("StringLiteralDuplication")
object Maven {
    /**
     * Encapsulates data about an [artifact](https://maven.apache.org/repositories/artifacts.html).
     * @property group The artifact group.
     * @property id The artifact id.
     * @throws IllegalArgumentException if [group] is blank.
     * @throws IllegalArgumentException if [id] is blank.
     * @author [Stanley Wintergreen](https://github.com/kepocnhh)
     * @since 0.5.1
     */
    class Artifact(val group: String, val id: String) {
        init {
            require(group.isNotBlank()) { "The group ID is blank!" }
            require(id.isNotBlank()) { "The artifact ID is blank!" }
        }

        /**
         * Usage:
         * ```
         * val artifact = Maven.Artifact(group = "foo", id = "bar")
         * assertEquals("foo:bar", artifact.moduleName())
         * ```
         * @return the [String] name of the entire artifact module according to Maven format.
         * @author [Stanley Wintergreen](https://github.com/kepocnhh)
         * @since 0.5.4
         */
        fun moduleName(): String {
            return "$group:$id"
        }

        /**
         * Usage:
         * ```
         * val artifact = Maven.Artifact(group = "foo", id = "bar")
         * assertEquals("foo:bar:0.1.2", artifact.moduleName("0.1.2"))
         * ```
         * @param version will be used in the name of the entire artifact module according to the Maven format.
         * @throws IllegalArgumentException if [version] is blank.
         * @author [Stanley Wintergreen](https://github.com/kepocnhh)
         * @since 0.5.4
         */
        fun moduleName(version: String): String {
            require(version.isNotBlank())
            return "$group:$id:$version"
        }

        /**
         * Usage:
         * ```
         * val artifact = Maven.Artifact(group = "foo", id = "bar")
         * assertEquals("bar-0.1.2", artifact.name("0.1.2"))
         * ```
         * @param version will be used in the artifact name according to the Maven format.
         * @throws IllegalArgumentException if [version] is blank.
         * @author [Stanley Wintergreen](https://github.com/kepocnhh)
         * @since 0.5.4
         */
        fun name(version: String): String {
            require(version.isNotBlank())
            return "$id-$version"
        }

        /**
         * Usage:
         * ```
         * val artifact = Maven.Artifact(group = "foo", id = "bar")
         * val xml = artifact.pom(
         *     version = "42",
         *     packaging = "jar",
         * )
         * assertEquals(XMLParser.parse(xml).getNode("project").getString("version"), "42")
         * ```
         * @return The [String] XML in Maven [POM](https://maven.apache.org/pom.html) format.
         * @author [Stanley Wintergreen](https://github.com/kepocnhh)
         * @since 0.5.4
         */
        fun pom(
            modelVersion: String = "4.0.0",
            version: String,
            packaging: String,
        ): String {
            return pom(
                modelVersion = modelVersion,
                groupId = group,
                artifactId = id,
                version = version,
                packaging = packaging,
            )
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

    /**
     * Usage:
     * ```
     * val xml = Maven.pom(
     *     groupId = "foo",
     *     artifactId = "bar",
     *     version = "42",
     *     packaging = "jar",
     * )
     * assertEquals(XMLParser.parse(xml).getNode("project").getString("version"), "42")
     * ```
     * @throws IllegalArgumentException if [modelVersion] is blank.
     * @throws IllegalArgumentException if [groupId] is blank.
     * @throws IllegalArgumentException if [artifactId] is blank.
     * @throws IllegalArgumentException if [version] is blank.
     * @throws IllegalArgumentException if [packaging] is blank.
     * @return The [String] XML in Maven [POM](https://maven.apache.org/pom.html) format.
     * @author [Stanley Wintergreen](https://github.com/kepocnhh)
     * @since 0.5.1
     */
    fun pom(
        modelVersion: String = "4.0.0",
        groupId: String,
        artifactId: String,
        version: String,
        packaging: String,
    ): String {
        require(modelVersion.isNotBlank()) { "The model version is blank!" }
        require(groupId.isNotBlank()) { "The group ID is blank!" }
        require(artifactId.isNotBlank()) { "The artifact ID is blank!" }
        require(version.isNotBlank()) { "The version is blank!" }
        require(packaging.isNotBlank()) { "The packaging is blank!" }
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

    /**
     * A set of functions and types for working with Maven snapshot [repositories](https://s01.oss.sonatype.org).
     * @author [Stanley Wintergreen](https://github.com/kepocnhh)
     * @since 0.2.3
     */
    object Snapshot {
        /**
         * Usage:
         * ```
         * val url = Maven.Snapshot.url(groupId = "foo", artifactId = "bar", version = "42")
         * assertEquals(cURL.get(url).code, 200)
         * ```
         * @throws IllegalArgumentException if [groupId] is blank.
         * @throws IllegalArgumentException if [artifactId] is blank.
         * @throws IllegalArgumentException if [version] is blank.
         * @return The [URL] to the Maven artifact.
         * @author [Stanley Wintergreen](https://github.com/kepocnhh)
         * @since 0.5.1
         */
        fun url(
            groupId: String,
            artifactId: String,
            version: String,
        ): URL {
            require(groupId.isNotBlank()) { "The group ID is blank!" }
            require(artifactId.isNotBlank()) { "The artifact ID is blank!" }
            require(version.isNotBlank()) { "The version is blank!" }
            val host = "https://s01.oss.sonatype.org"
            val path = "content/repositories/snapshots"
            val spec = "$host/$path/${groupId.replace('.', '/')}/$artifactId/$version"
            return URL(spec)
        }

        /**
         * Usage:
         * ```
         * val artifact = Maven.Artifact(group = "foo", id = "bar")
         * val url = Maven.Snapshot.url(artifact = artifact, version = "42")
         * assertEquals(cURL.get(url).code, 200)
         * ```
         * @return The [URL] to the Maven artifact.
         * @author [Stanley Wintergreen](https://github.com/kepocnhh)
         * @since 0.4.3
         */
        fun url(
            artifact: Artifact,
            version: String,
        ): URL {
            return url(
                groupId = artifact.group,
                artifactId = artifact.id,
                version = version,
            )
        }
    }
}
