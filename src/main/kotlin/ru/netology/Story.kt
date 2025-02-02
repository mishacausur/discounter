package ru.netology

class StoryAttachment(
    override val type: Media = Media.STORY,
    val story: Story
) : Attachment {
}

data class Story(
    val id: UInt,
    val ownerId: UInt,
    var expiresAt: UInt?,
    var seen: Boolean?,
)