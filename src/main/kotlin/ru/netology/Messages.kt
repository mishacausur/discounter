package ru.netology

data class Message(
    val id: UInt,
    var text: String
    ) {}

data class Group(
    val id: UInt,
    var messages: List<Message>
)



object MessageService {
    
}