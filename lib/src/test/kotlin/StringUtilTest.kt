@file:Suppress("MissingPackageDeclaration")

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class StringUtilTest {
    @Test
    fun taskTest() {
        assertEquals("fooBarBaz", "foo".task("bar", "baz"))
        assertEquals("fooBaz", "foo".task("", "baz"))
        assertEquals("fooBar", "foo".task("bar", ""))
        assertEquals("fooBar", "foo".task("bar"))
        assertEquals("foo", "foo".task("", ""))
        assertEquals("foo", "foo".task(""))
    }

    @Test
    fun taskErrorTest() {
        assertThrows(IllegalStateException::class.java) {
            @Suppress("IgnoredReturnValue")
            "".task("foo")
        }
    }

    @Test
    fun versionTest() {
        assertEquals("foo-bar-baz", "foo".version("bar", "baz"))
        assertEquals("foo-baz", "foo".version("", "baz"))
        assertEquals("foo-bar", "foo".version("bar", ""))
        assertEquals("foo-bar", "foo".version("bar"))
        assertEquals("foo", "foo".version("", ""))
        assertEquals("foo", "foo".version(""))
    }

    @Test
    fun versionErrorTest() {
        assertThrows(IllegalStateException::class.java) {
            @Suppress("IgnoredReturnValue")
            "".version("foo")
        }
    }

    @Test
    fun uppercaseFirstCharTest() {
        assertEquals("Foo", "foo".uppercaseFirstChar())
        assertEquals("Foo", "Foo".uppercaseFirstChar())
        assertEquals("0", "0".uppercaseFirstChar())
        assertEquals("", "".uppercaseFirstChar())
    }
}
