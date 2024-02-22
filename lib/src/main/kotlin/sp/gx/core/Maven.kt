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

        fun moduleName(): String {
            return "$group:$id"
        }

        fun moduleName(version: String): String {
            require(version.isNotBlank())
            return "$group:$id:$version"
        }


        fun name(version: String): String {
            require(version.isNotBlank())
            return "$id:$version"
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
     * Usage:
     * ```
     * val artifact = Maven.Artifact(group = "foo", id = "bar")
     * val xml = Maven.pom(
     *     artifact = artifact,
     *     version = "42",
     *     packaging = "jar",
     * )
     * assertEquals(XMLParser.parse(xml).getNode("project").getString("version"), "42")
     * ```
     * @return The [String] XML in Maven [POM](https://maven.apache.org/pom.html) format.
     * @author [Stanley Wintergreen](https://github.com/kepocnhh)
     * @since 0.4.3
     */
    fun pom(
        modelVersion: String = "4.0.0",
        artifact: Artifact,
        version: String,
        packaging: String,
    ): String {
        return pom(
            modelVersion = modelVersion,
            groupId = artifact.group,
            artifactId = artifact.id,
            version = version,
            packaging = packaging,
        )
    }

    /**
     * Usage:
     * ```
     * val xml = Maven.metadata(
     *     groupId = "foo",
     *     artifactId = "bar",
     *     version = "42",
     * )
     * assertEquals(XMLParser.parse(xml).getNode("metadata").getString("groupId"), "foo")
     * ```
     * @throws IllegalArgumentException if [groupId] is blank.
     * @throws IllegalArgumentException if [artifactId] is blank.
     * @throws IllegalArgumentException if [version] is blank.
     * @return The [String] XML in Maven [metadata](https://maven.apache.org/repositories/metadata.html) format.
     * @author [Stanley Wintergreen](https://github.com/kepocnhh)
     * @since 0.5.1
     */
    @Deprecated(message = "deprecated in 0.5.0 | use {varinant}/maven/assemble/metadata.sh", level = DeprecationLevel.WARNING)
    fun metadata(
        groupId: String,
        artifactId: String,
        version: String,
        dateTime: LocalDateTime = LocalDateTime.now(),
        dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss"),
    ): String {
        require(groupId.isNotBlank()) { "The group ID is blank!" }
        require(artifactId.isNotBlank()) { "The artifact ID is blank!" }
        require(version.isNotBlank()) { "The version is blank!" }
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

    /**
     * Usage:
     * ```
     * val artifact = Maven.Artifact(group = "foo", id = "bar")
     * val xml = Maven.metadata(
     *     artifact = artifact,
     *     version = "42",
     * )
     * assertEquals(XMLParser.parse(xml).getNode("metadata").getString("groupId"), "foo")
     * ```
     * @return The [String] XML in Maven [metadata](https://maven.apache.org/repositories/metadata.html) format.
     * @author [Stanley Wintergreen](https://github.com/kepocnhh)
     * @since 0.4.3
     */
    @Deprecated(message = "deprecated in 0.5.0 | use {varinant}/maven/assemble/metadata.sh", level = DeprecationLevel.WARNING)
    fun metadata(
        artifact: Artifact,
        version: String,
        dateTime: LocalDateTime = LocalDateTime.now(),
        dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss"),
    ): String {
        return metadata(
            groupId = artifact.group,
            artifactId = artifact.id,
            version = version,
            dateTime = dateTime,
            dateTimeFormatter = dateTimeFormatter,
        )
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
