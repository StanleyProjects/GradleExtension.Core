package sp.gx.core

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.net.URL

internal class MavenSnapshotTest {
    @Test
    fun urlTest() {
        val actual = Maven.Snapshot.url(
            groupId = "foo",
            artifactId = "bar",
            version = "42",
        )
        val expected = URL("https://s01.oss.sonatype.org/content/repositories/snapshots/foo/bar/42")
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun urlErrorTest() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.Snapshot.url(
                groupId = "",
                artifactId = "",
                version = "",
            )
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.Snapshot.url(
                groupId = " ",
                artifactId = "",
                version = "",
            )
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.Snapshot.url(
                groupId = "foo",
                artifactId = "",
                version = "",
            )
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.Snapshot.url(
                groupId = "foo",
                artifactId = " ",
                version = "",
            )
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.Snapshot.url(
                groupId = "foo",
                artifactId = "bar",
                version = "",
            )
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Maven.Snapshot.url(
                groupId = "foo",
                artifactId = "bar",
                version = " ",
            )
        }
    }
}
