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
     * @throws IllegalStateException if [group] is empty.
     * @throws IllegalStateException if [id] is empty.
     * @author [Stanley Wintergreen](https://github.com/kepocnhh)
     * @since 0.2.3
     */
    class Artifact(val group: String, val id: String) {
        init {
            check(group.isNotEmpty()) { "Group ID is empty!" }
            check(id.isNotEmpty()) { "Artifact ID is empty!" }
        }

        // todo module name group:id/group:id:version

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
     * @throws IllegalStateException if [modelVersion] is empty.
     * @throws IllegalStateException if [groupId] is empty.
     * @throws IllegalStateException if [artifactId] is empty.
     * @throws IllegalStateException if [version] is empty.
     * @throws IllegalStateException if [packaging] is empty.
     * @return The [String] XML in Maven [POM](https://maven.apache.org/pom.html) format.
     * @author [Stanley Wintergreen](https://github.com/kepocnhh)
     * @since 0.2.3
     */
    fun pom(
        modelVersion: String = "4.0.0",
        groupId: String,
        artifactId: String,
        version: String,
        packaging: String,
    ): String {
        check(modelVersion.isNotEmpty()) { "Model version is empty!" }
        check(groupId.isNotEmpty()) { "Group ID is empty!" }
        check(artifactId.isNotEmpty()) { "Artifact ID is empty!" }
        check(version.isNotEmpty()) { "Version is empty!" }
        check(packaging.isNotEmpty()) { "Packaging is empty!" }
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
     * @throws IllegalStateException if [groupId] is empty.
     * @throws IllegalStateException if [artifactId] is empty.
     * @throws IllegalStateException if [version] is empty.
     * @return The [String] XML in Maven [metadata](https://maven.apache.org/repositories/metadata.html) format.
     * @author [Stanley Wintergreen](https://github.com/kepocnhh)
     * @since 0.2.3
     */
    fun metadata(
        groupId: String,
        artifactId: String,
        version: String,
        dateTime: LocalDateTime = LocalDateTime.now(),
        dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss"),
    ): String {
        check(groupId.isNotEmpty()) { "Group ID is empty!" }
        check(artifactId.isNotEmpty()) { "Artifact ID is empty!" }
        check(version.isNotEmpty()) { "Version is empty!" }
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
         * @throws IllegalStateException if [groupId] is empty.
         * @throws IllegalStateException if [artifactId] is empty.
         * @throws IllegalStateException if [version] is empty.
         * @return The [URL] to the Maven artifact.
         * @author [Stanley Wintergreen](https://github.com/kepocnhh)
         * @since 0.2.3
         */
        fun url(
            groupId: String,
            artifactId: String,
            version: String,
        ): URL {
            check(groupId.isNotEmpty()) { "Group ID is empty!" }
            check(artifactId.isNotEmpty()) { "Artifact ID is empty!" }
            check(version.isNotEmpty()) { "Version is empty!" }
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
