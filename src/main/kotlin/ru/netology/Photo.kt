package ru.netology

class PhotoAttachment(
    override val type: Media = Media.PHOTO,
    val photo: Photo
) : Attachment {
    override fun toString(): String {
        return "photo ${photo.text}"
    }
}

data class Photo(
    val id: UInt,
    val ownerId: UInt,
    var albumId: UInt?,
    var text: String?,
)