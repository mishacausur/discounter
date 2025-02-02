package ru.netology

class StickerAttachment(
    override val type: Media = Media.STICKER,
    val sticker: Sticker
): Attachment

data class Sticker(
    val stickerId: UInt,
    val productId: UInt,
    var isAllowed: Boolean?,
)