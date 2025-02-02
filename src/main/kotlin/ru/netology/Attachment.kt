package ru.netology

interface Attachment {
    val type: Media
}

enum class Media {
    VIDEO, PHOTO, AUDIO, STICKER, STORY
}
