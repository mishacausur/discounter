package ru.netology

import kotlin.math.max

fun main() {
    task3()
}

fun task1() {
    val amount = 1200

    val feePercent = (amount * 0.75) / 100

    val minFeeAmount = 35

    val fee = max(
        feePercent.toInt(),
        minFeeAmount
    )

    println("Комиссия за перевод составит $fee руб.")
}

fun task2() {
    val likes = 31

    val single = "человеку"
    val plural = "людям"

    val result = if (likes.toString().last() == '1') single else plural

    println("Понравилось $likes $result")
}

fun task3() {
    val isLoyalCustomer = true
    val amount = 10004
    var result = 0
//    if (amount in 0..1_000) {
//        result = amount
//    } else if (amount in 1_001..10_000) {
//        result = amount - 100
//    } else if (amount > 10_000) {
//        result = amount - (amount * 5 / 100)
//    } else {
//        result = 0
//    }

    when (amount) {
        in 0..1_000 -> result = amount
        in 1_001..10_000 -> result = amount - 100
        in 10_001..Int.MAX_VALUE -> result = amount - (amount * 5 / 100)
        else -> result = 0
    }

    if (isLoyalCustomer) {
        result = result - (result * 1) / 100
    }

    println("Итоговая стоимость покупки составляет $result руб.")
}