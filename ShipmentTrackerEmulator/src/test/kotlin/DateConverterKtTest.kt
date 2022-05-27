import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class DateConverterKtTest {
    @Test
    fun dateConverter() {
        assertEquals("2022.05.16 08:54", dateConverter(1652712855468))
    }

    @Test
    fun dateConverterNull() {
        assertEquals("Unavailable", dateConverter(null))
    }
}