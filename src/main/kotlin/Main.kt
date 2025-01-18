package ru.netology

import kotlin.math.max

fun main() {
    task2()
}

// Задача 1 - Денежные переводы
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

// Задача 2 - Люди/Человеки
fun task2() {
    val likes = 1011

    val single = "человеку"
    val plural = "людям"

    val likesString = likes.toString()
    val result = if (likesString.last() == '1' && likesString.takeLast(2) != "11") single else plural

    println("Понравилось $likes $result")
}

// Задача 3 - Меломан
fun task3() {
    val isLoyalCustomer = true
    val amount = 10004
    var result = 0

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