package sp.gx.core

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class SlashCaseTest {
    @Test
    fun checkSlashCase() {
        Assertions.assertEquals("foo/bar/baz", slashCase("foo", "bar", "baz"))
        Assertions.assertEquals("foo/baz", slashCase("foo", "", "baz"))
        Assertions.assertEquals("foo/baz", slashCase("foo", " ", "baz"))
        Assertions.assertEquals("foo/bar", slashCase("foo", "bar", ""))
        Assertions.assertEquals("foo/bar", slashCase("foo", "bar"))
        Assertions.assertEquals("foo", slashCase("foo", "", ""))
        Assertions.assertEquals("foo", slashCase("foo", ""))
        Assertions.assertEquals("foo", slashCase("foo"))
    }

    @Test
    fun slashCaseErrorTest() {
        Assertions.assertThrows(IllegalStateException::class.java) {
            @Suppress("IgnoredReturnValue")
            slashCase("", "bar")
        }
    }
}
