package ru.netology

class VideoAttachment(
    override val type: Media = Media.VIDEO,
    val video: Video
) : Attachment {
    override fun toString(): String {
        return "video ${video.title}"
    }
}

data class Video(
    val id: UInt,
    val ownerId: UInt,
    var title: String,
    var description: String?,
    val duration: UInt
)