package sp.gx.core

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class MavenTest {
    @Test
    fun pomTest() {
        val actual = Maven.pom(
            groupId = "foo",
            artifactId = "bar",
            version = "42",
            packaging = "baz",
        )
        val expected = "<project xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\" xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><modelVersion>4.0.0</modelVersion><groupId>foo</groupId><artifactId>bar</artifactId><version>42</version><packaging>baz</packaging></project>"
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun metadataTest() {
        val actual = Maven.metadata(
            groupId = "foo",
            artifactId = "bar",
            version = "42",
        )
        val expected = """
            <metadata>
                <groupId>foo</groupId>
                <artifactId>bar</artifactId>
                <versioning>
                    <versions>
                        <version>42</version>
                    </versions>
                    <lastUpdated>?</lastUpdated>
                </versioning>
            </metadata>
        """.trimIndent()
        Assertions.assertEquals(expected, actual)
    }
}
