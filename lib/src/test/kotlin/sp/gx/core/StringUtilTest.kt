package sp.gx.core

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class StringUtilTest {
    @Test
    fun camelCaseTest() {
        assertEquals("fooBarBaz", camelCase("foo", "bar", "baz"))
        assertEquals("fooBaz", camelCase("foo", "", "baz"))
        assertEquals("fooBar", camelCase("foo", "bar", ""))
        assertEquals("fooBar", camelCase("foo", "bar"))
        assertEquals("foo", camelCase("foo", "", ""))
        assertEquals("foo", camelCase("foo", ""))
    }

    @Test
    fun camelCaseErrorTest() {
        assertThrows(IllegalStateException::class.java) {
            @Suppress("IgnoredReturnValue")
            camelCase("", "foo")
        }
    }

    @Test
    fun kebabCaseTest() {
        assertEquals("foo-bar-baz", kebabCase("foo", "bar", "baz"))
        assertEquals("foo-baz", kebabCase("foo", "", "baz"))
        assertEquals("foo-bar", kebabCase("foo", "bar", ""))
        assertEquals("foo-bar", kebabCase("foo", "bar"))
        assertEquals("foo", kebabCase("foo", "", ""))
        assertEquals("foo", kebabCase("foo", ""))
    }

    @Test
    fun kebabCaseErrorTest() {
        assertThrows(IllegalStateException::class.java) {
            @Suppress("IgnoredReturnValue")
            kebabCase("", "foo")
        }
    }

    @Test
    fun uppercaseFirstCharTest() {
        assertEquals("Foo", "foo".uppercaseFirstChar())
        assertEquals("Foo", "Foo".uppercaseFirstChar())
        assertEquals("0", "0".uppercaseFirstChar())
        assertEquals("", "".uppercaseFirstChar())
    }

    @Test
    fun filledTest() {
        val expected = "foo"
        check(expected.isNotEmpty())
        assertEquals(expected, expected.filled())
    }

    @Test
    fun filledNotTest() {
        val expected = ""
        check(expected.isEmpty())
        assertThrows(IllegalStateException::class.java) {
            @Suppress("IgnoredReturnValue")
            expected.filled()
        }
    }

    @Test
    fun colonCaseTest() {
        assertEquals("foo:bar:baz", colonCase("foo", "bar", "baz"))
        assertEquals("foo:baz", colonCase("foo", "", "baz"))
        assertEquals("foo:bar", colonCase("foo", "bar", ""))
        assertEquals("foo:bar", colonCase("foo", "bar"))
        assertEquals("foo", colonCase("foo", "", ""))
        assertEquals("foo", colonCase("foo", ""))
    }

    @Test
    fun colonCaseErrorTest() {
        assertThrows(IllegalStateException::class.java) {
            @Suppress("IgnoredReturnValue")
            colonCase("", "bar")
        }
    }

    @Test
    fun slashCaseTest() {
        assertEquals("foo/bar/baz", slashCase("foo", "bar", "baz"))
        assertEquals("foo/baz", slashCase("foo", "", "baz"))
        assertEquals("foo/bar", slashCase("foo", "bar", ""))
        assertEquals("foo/bar", slashCase("foo", "bar"))
        assertEquals("foo", slashCase("foo", "", ""))
        assertEquals("foo", slashCase("foo", ""))
    }

    @Test
    fun slashCaseErrorTest() {
        assertThrows(IllegalStateException::class.java) {
            @Suppress("IgnoredReturnValue")
            slashCase("", "bar")
        }
    }
}
