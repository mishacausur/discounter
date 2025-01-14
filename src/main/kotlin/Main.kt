package ru.netology

import kotlin.math.max

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    task2()
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