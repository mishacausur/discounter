package ru.netology

import kotlin.math.abs
import kotlin.math.max

fun main() {
    //println(agoToText(720))
    task2()
}

// Задача 1 - Когда собеседник был онлайн
fun agoToText(seconds: Int): String {
    val hour = 60 * 60
    val day = hour * 24
    return when (seconds) {
        in 0..60 -> "был(а) только что"
        in 61..hour -> "был(а) ${extractMinutes(seconds / 60)} назад"
        in hour + 1..day -> "был(а) ${extractHour(seconds / hour)} назад"
        in day..2 * day -> "был(а) вчера"
        in day * 2..day * 3 -> "был(а) позавчера"
        in day * 3..Int.MAX_VALUE -> "был(а) давно"
        else -> "был(а) давно"
    }
}

fun extractMinutes(minutes: Int): String = when {
    arrayOf("11", "12", "13", "14").contains(minutes.toString().takeLast(2)) -> "${minutes} минут"
    minutes.toString().last() == '1' -> "${minutes} минуту"
    minutes % 10 in 2..4 -> "${minutes} минуты"
    else -> "${minutes} минут"
}

fun extractHour(hours: Int): String = when {
    arrayOf("11", "12", "13", "14").contains(hours.toString().takeLast(2)) -> "${hours} часов"
    hours.toString().last() == '1' -> "${hours} час"
    hours % 10 in 2..4 -> "${hours} часа"
    else -> "${hours} часов"
}

// Задача 2 - Разная комиссия
enum class CardType {
    MIR, VISA, MASTERCARD
}

fun task2() {
    val card = CardType.MASTERCARD
    val dailyLimit = 150_000
    val monthLimit = 600_000

    var dailyTransferSum = 0
    var monthTransferSum = 120_000
    var cardBalance = 500_000
    val amount = 100_000

    if ((dailyTransferSum + amount) > dailyLimit || (monthTransferSum + amount) > monthLimit) {
        println("Превышен лимит переводов")
    } else if (cardBalance - amount < 0) {
        println("Недостаточно средств для соверешения перевода")
    } else {
        val commisiion = calculateCommission(card, monthTransferSum, amount)
        dailyTransferSum += amount
        monthTransferSum += amount
        cardBalance -= (amount + commisiion)
        println("Совершен перевод на сумму ${amount} руб., комиссия за перевод составила ${commisiion} руб.")
    }
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