package sp.gx.core

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class KebabCaseTest {
    @Test
    fun checkKebabCase() {
        Assertions.assertEquals("foo-bar-baz", kebabCase("foo", "bar", "baz"))
        Assertions.assertEquals("foo-baz", kebabCase("foo", "", "baz"))
        Assertions.assertEquals("foo-baz", kebabCase("foo", " ", "baz"))
        Assertions.assertEquals("foo-bar", kebabCase("foo", "bar", ""))
        Assertions.assertEquals("foo-bar", kebabCase("foo", "bar"))
        Assertions.assertEquals("foo", kebabCase("foo", "", ""))
        Assertions.assertEquals("foo", kebabCase("foo", ""))
    }

    @Test
    fun kebabCaseErrorTest() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            kebabCase("", "foo")
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            kebabCase(" ", "foo")
        }
    }
}
