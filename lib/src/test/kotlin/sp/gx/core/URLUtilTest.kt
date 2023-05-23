package sp.gx.core

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.net.URL

internal class URLUtilTest {
    @Test
    fun resolveTest() {
        val actual = URL("http://foo").resolve("bar")
        Assertions.assertEquals(URL("http://foo/bar"), actual)
    }
}
