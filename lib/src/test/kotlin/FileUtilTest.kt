import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import java.io.File

internal class FileUtilTest {
    @Test
    fun existingTest() {
        val file = File.createTempFile("foo", "bar")
        check(file.exists())
        assertEquals(file, file.existing())
    }

    @Test
    fun existingNotTest() {
        val file = File("foo")
        check(!file.exists())
        assertThrows(IllegalStateException::class.java) {
            file.existing()
        }
    }
}
