package ru.netology

import kotlin.math.abs
import kotlin.math.max

fun main() {
    //task2()
}

enum class CardType {
    MIR, VISA, MASTERCARD
}

fun calculateCommission(cardType: CardType = CardType.MIR, pastTransferAmount: Int = 0, amount: Int): Int {
    when (cardType) {
        CardType.MIR -> return 0
        CardType.VISA -> return max(((amount * 0.75) / 100).toInt(), 35)
        CardType.MASTERCARD -> return calculateMasterCardCommission(pastTransferAmount, amount)
    }
}

fun calculateMasterCardCommission(pastTransferAmount: Int, amount: Int): Int {
    val limit = 75_000
    when {
        pastTransferAmount > limit -> return getMCCommission(amount).toInt()
        (pastTransferAmount + amount) > limit -> {
            val residue = limit - pastTransferAmount
            return getMCCommission(amount - residue).toInt()
        }

        else -> return 0
    }
}

fun getMCCommission(amount: Int): Double {
    return ((abs(amount) * 0.6) / 100) + 20
}