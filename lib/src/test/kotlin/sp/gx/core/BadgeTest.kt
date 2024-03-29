package sp.gx.core

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class BadgeTest {
    @Test
    fun urlTest() {
        val actual = Badge.url(
            label = "foo",
            message = "bar",
            color = "baz",
        )
        val params = "label=foo&message=bar&labelColor=212121&color=baz&style=flat"
        @Suppress("NullableToStringCall")
        Assertions.assertEquals("https://img.shields.io/static/v1?$params", actual.toString())
    }

    @Test
    @Suppress("LongMethod")
    fun urlErrorTest() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Badge.url(
                label = " ",
                message = "",
                color = "",
            )
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Badge.url(
                label = "foo",
                message = " ",
                color = "",
            )
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Badge.url(
                label = "foo",
                message = "bar",
                color = " ",
            )
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Badge.url(
                label = "",
                message = "",
                color = "",
            )
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Badge.url(
                label = "foo",
                message = "",
                color = "",
            )
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Badge.url(
                label = "foo",
                message = "bar",
                labelColor = "",
                color = "",
            )
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Badge.url(
                label = "foo",
                message = "bar",
                color = "",
            )
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            @Suppress("IgnoredReturnValue")
            Badge.url(
                label = "foo",
                message = "bar",
                color = "baz",
                style = "",
            )
        }
    }
}
