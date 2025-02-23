package ru.netology

data class Message(
    val id: UInt,
    var text: String,
    var isRead: Boolean = false
    )

data class Conversation(
    val id: UInt,
    val personId: UInt,
    val messages: MutableList<Message> = mutableListOf<Message>()
)

object MessageService {
    private val conversations = mutableListOf<Conversation>()
    private var latsConversationId: UInt = 0u
    private var lastMessageId: UInt = 0u

    fun createConversation(personId: UInt): Conversation {
        return conversations.find { it.personId == personId }
            ?: Conversation(
                id = ++latsConversationId,
                personId = personId
            ).also { conversations.add(it) }
    }

    fun sendMessage(personId: UInt, text: String) {
        createConversation(personId).messages.add(Message(++lastMessageId, text))
    }

    fun deleteMessage(messageId: UInt, conversationId: UInt) {
        conversations
            .asSequence()
            .find { it.id == conversationId }
            ?.messages
            ?.removeIf { it.id == messageId }
    }

    fun clearConversation(conversationId: UInt) {
        conversations.removeIf { it.id == conversationId }
    }

    fun getAllConversations(): List<Conversation> = conversations

    fun getLastMessages(): List<String> {
        return conversations
            .asSequence()
            .map { it.messages.lastOrNull()?.text ?: "Нет сообщений" }
            .toList()
    }

    fun getInreadConversations(): UInt {
        return conversations
            .asSequence()
            .filter { it.messages.any { !it.isRead }  }
            .count()
            .toUInt()
    }

    fun getMessages(personId: UInt, messagesCount: UInt): List<Message> {
        return conversations
            .find { it.personId == personId }
            ?.messages
            ?.asSequence()
            ?.sortedByDescending { it.id }
            ?.take(messagesCount.toInt())
            ?.onEach { it.isRead = true }
            ?.toList()?: emptyList()
    }

    fun reset() {
        latsConversationId = 0u
        lastMessageId = 0u
        conversations.clear()
    }
}