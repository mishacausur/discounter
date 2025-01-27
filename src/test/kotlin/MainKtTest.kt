import org.junit.Test
import junit.framework.TestCase.assertEquals
import ru.netology.CardType
import ru.netology.calculateCommission

class MainKtTest {

    // MASCTERCARD
    @Test
    fun calculateMasterCardOverLimit() {
        val commission = 320 // 50_000 * 6% + 20 rub
        val result = calculateCommission(CardType.MASTERCARD, amount = 125_000)
        assertEquals(commission, result)
    }

    @Test
    fun calculateMasterCardOverPassLimit() {
        val commission = 1290 // 45_000 * 6% + 20 rub
        val result = calculateCommission(CardType.MASTERCARD, 125_000, 45_000)
        assertEquals(commission, result)
    }

    @Test
    fun calculateMasterCardLessLimit() {
        val commission = 0 // 25_000 < 75_000 -> 0
        val result = calculateCommission(CardType.MASTERCARD, amount = 25_000)
        assertEquals(commission, result)
    }

    // VISA
    @Test
    fun calculateVisaOverConstant() {
        val commission = 187 // 25_000 * 0.75% = 187
        val result = calculateCommission(CardType.VISA, amount = 25_000)
        assertEquals(commission, result)
    }

    @Test
    fun calculateVisaLEssConstant() {
        val commission = 35 // 1_500 * 0.75% < 35 -> 35
        val result = calculateCommission(CardType.VISA, amount = 1_500)
        assertEquals(commission, result)
    }

    // MIR
    @Test
    fun calculateMir() {
        val commission = 0
        val result = calculateCommission(CardType.MIR, amount = 10_500)
        assertEquals(commission, result)
    }
}