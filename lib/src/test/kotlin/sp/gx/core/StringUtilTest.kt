package sp.gx.core

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class StringUtilTest {
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
}
