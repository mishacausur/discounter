package ru.netology

import kotlin.math.max
import kotlin.math.min

fun main() {
    println(agoToText(90921))
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
    minutes.toString().takeLast(2) == "11" -> "${minutes} минут"
    minutes.toString().last() == '1' -> "${minutes} минуту"
    minutes % 10 in 2..4 -> "${minutes} минуты"
    else -> "${minutes} минут"
}

fun extractHour(hours: Int): String = when {
    hours.toString().takeLast(2) == "11" -> "${hours} часов"
    hours.toString().last() == '1' -> "${hours} час"
    hours % 10 in 2..4 -> "${hours} часа"
    else -> "${hours} часов"
}

// Задача 2 - Разная комиссия
fun task2() {
    val likes = 11

    val single = "человеку"
    val plural = "людям"

    val likesString = likes.toString()
    val result = if (likesString.last() == '1' && likesString != "11") single else plural

    println("Понравилось $likes $result")
}
