package ru.netology

import javax.swing.table.TableStringConverter

data class Message(
    val id: UInt,
    var text: String,
    var isRead: Boolean = false
    ) {}

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
        conversations.find { it.id == conversationId }
            ?.apply { messages.removeIf { it.id == messageId} }
    }

    fun clearConversation(conversationId: UInt) {
        conversations.removeIf { it.id == conversationId }
    }

    fun getAllConversations(): List<Conversation> = conversations

    fun getLastMessages(): List<String> {
        return conversations.map { it.messages.lastOrNull()?.text ?: "Нет сообщений" }
    }

    fun getInreadConversations(): UInt {
        return conversations.count { it.messages.any { !it.isRead }}.toUInt()
    }

    fun getMessages(personId: UInt, messagesCount: UInt): List<Message> {
        return conversations
            .find { it.personId == personId }
            ?.messages
            ?.takeLast(messagesCount.toInt())
            ?.onEach { it.isRead = true } ?: emptyList()
    }
}