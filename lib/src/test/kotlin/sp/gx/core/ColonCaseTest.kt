package sp.gx.core

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class ColonCaseTest {
    @Test
    fun checkColonCase() {
        Assertions.assertEquals("foo:bar:baz", colonCase("foo", "bar", "baz"))
        Assertions.assertEquals("foo:baz", colonCase("foo", "", "baz"))
        Assertions.assertEquals("foo:baz", colonCase("foo", " ", "baz"))
        Assertions.assertEquals("foo:bar", colonCase("foo", "bar", ""))
        Assertions.assertEquals("foo:bar", colonCase("foo", "bar"))
        Assertions.assertEquals("foo", colonCase("foo", "", ""))
        Assertions.assertEquals("foo", colonCase("foo", ""))
    }

    @Test
    fun colonCaseErrorTest() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            colonCase("", "bar")
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            colonCase(" ", "bar")
        }
    }
}
