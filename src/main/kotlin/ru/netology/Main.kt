package ru.netology

data class Post(
    val id: UInt,
    val ownerId: UInt,
    val date: Int,
    val text: String,
    val views: UInt,
    val markedAsAds: Boolean,
    val likes: UInt,
    val canLike: Boolean,
    val userLiked: Boolean,
    val canPublish: Boolean,
)

fun main() {

}
