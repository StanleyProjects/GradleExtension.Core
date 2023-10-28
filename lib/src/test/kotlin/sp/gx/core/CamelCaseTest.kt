package sp.gx.core

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class CamelCaseTest {
    @Test
    fun checkCamelCase() {
        Assertions.assertEquals("fooBarBaz", camelCase("foo", "bar", "baz"))
        Assertions.assertEquals("fooBaz", camelCase("foo", "", "baz"))
        Assertions.assertEquals("fooBar", camelCase("foo", "bar", ""))
        Assertions.assertEquals("fooBar", camelCase("foo", "bar"))
        Assertions.assertEquals("foo", camelCase("foo", "", ""))
        Assertions.assertEquals("foo", camelCase("foo", ""))
    }

    @Test
    fun camelCaseErrorTest() {
        Assertions.assertThrows(IllegalStateException::class.java) {
            @Suppress("IgnoredReturnValue")
            camelCase("", "foo")
        }
    }
}
