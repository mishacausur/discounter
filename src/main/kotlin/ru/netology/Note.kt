package ru.netology

abstract class Blank {
    var id: UInt = 0u
    var isDeleted: Boolean = false
}

enum class NotePrivacy(val raw: UInt) {
    ALL(0u),
    FRIENDS(1u),
    FRIENDSOFFRIENDS(2u),
    USER(3u)
}

data class Note(
    var title: String,
    var text: String,
    var privacy: NotePrivacy,

    ) : Blank()

data class NoteComment(
    val noteId: UInt,
    var message: String,
) : Blank()

class BlankCollection<B : Blank> {
    private val items = mutableListOf<B>()
    private var id: UInt = 0u

    fun add(item: B): UInt {
        id++
        item.id = id
        items.add(item)
        return items.last().id
    }

    fun delete(id: UInt): UInt {
        val index = getIndexAndCheckAccess(id) ?: return 0u
        items[index].isDeleted = true
        return 1u
    }

    fun getById(id: UInt): B? {
        return items.firstOrNull {
            it.id == id && !it.isDeleted
        }
    }

    fun update(item: B): UInt {
        val index = getIndexAndCheckAccess(item.id) ?: return 0u
        items[index] = item
        return 1u
    }

    fun restore(id: UInt): UInt {
        val index = items.indexOfFirst { it.id == id }
        if (index == -1) throw ItemNotFound()
        items[index].isDeleted = false
        return 1u
    }

    fun getAll(): List<B> {
        return items.filter { !it.isDeleted }
    }
    private fun getIndexAndCheckAccess(id: UInt): Int? {
        for ((index, item) in items.withIndex()) {
            if (item.id == id) {
                if (item.isDeleted) {
                    throw ItemIsDeleted()
                }
                return index
            }
        }
        return null
    }

    fun reset() {
        items.clear()
        id = 0u
    }
}

class ItemIsDeleted : Exception("Item is deleted")
class ItemNotFound : Exception("Item doesnt exist")

object NoteService {
    private val notes = BlankCollection<Note>()
    private val comments = BlankCollection<NoteComment>()

    fun add(note: Note): UInt {
        return notes.add(note)
    }

    fun createComment(comment: NoteComment): UInt {
        val note = notes.getById(comment.noteId) ?: throw ItemNotFound()
        return comments.add(comment)
    }

    fun delete(id: UInt): UInt {
        val deleted = notes.delete(id)
        if (deleted == 1u) {
            comments.getAll()
                .filter { it.noteId == id }
                .forEach { it.isDeleted = true }
        }
        return deleted
    }

    fun deleteComment(id: UInt): UInt {
        return comments.delete(id)
    }

    fun edit(note: Note): UInt {
        return notes.update(note)
    }

    fun editComment(comment: NoteComment): UInt {
        return comments.update(comment)
    }

    fun get(): List<Note> {
        return notes.getAll()
    }

    fun getById(id: UInt): Note? {
        return notes.getById(id)
    }

    fun getComments(noteId: UInt): List<NoteComment> {
        return comments.getAll()
            .filter { it.noteId == noteId }
    }

    fun restoreComment(id: UInt): UInt {
        return comments.restore(id)
    }

    fun reset() {
        notes.reset()
        comments.reset()
    }
}