package ru.netology

enum class MediaType {
    VIDEO, PHOTO, AUDIO, STICKER, STORY
}

sealed class Attachment(val type: MediaType)

data class Video(
    val id: UInt,
    val ownerId: UInt,
    var title: String,
    var description: String?,
    val duration: UInt,
) : Attachment(MediaType.VIDEO)

data class Photo(
    val id: UInt,
    val ownerId: UInt,
    var albumId: UInt?,
    var text: String?,
) : Attachment(MediaType.PHOTO)

data class Audio(
    val id: UInt,
    val ownerId: UInt,
    val artist: String,
    var albumId: UInt?,
    var title: String,
    val duration: UInt,
    val lyricsId: UInt?,
) : Attachment(MediaType.AUDIO)

data class Sticker(
    val stickerId: UInt,
    val productId: UInt,
    var isAllowed: Boolean?,
) : Attachment(MediaType.STICKER)

data class Story(
    val id: UInt,
    val ownerId: UInt,
    var expiresAt: UInt?,
    var seen: Boolean?,
) : Attachment(MediaType.STORY)