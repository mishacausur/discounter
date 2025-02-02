package ru.netology

class AudioAttachment(
    override val type: Media = Media.AUDIO,
    val audio: Audio
) : Attachment {
    override fun toString(): String {
        return "photo ${audio.title}"
    }
}

data class Audio(
    val id: UInt,
    val ownerId: UInt,
    val artist: String,
    var albumId: UInt?,
    var title: String,
    val duration: UInt,
    val lyricsId: UInt?,
)