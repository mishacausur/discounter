import org.junit.Test
import junit.framework.TestCase.assertEquals
import org.junit.Before
import ru.netology.*

class MainKtTest {

    @Before
    fun resetWallService() {
        WallService.reset()
        NoteService.reset()
        MessageService.reset()
    }

    fun mockAttachment(): Array<Attachment> {
        val photo = Photo(
            1u, 1u, 1u, null
        )

        val sticker = Sticker(500u, 510u, false)

        return arrayOf(photo, sticker)
    }

    fun makeMock(id: UInt = 0u): Post {
        return Post(
            id,
            111u,
            1000,
            "Hello there! hope you have a really beautiful day",
            0u,
            false,
            0u,
            false,
            false,
            false,
            emptyArray(),
            mockAttachment()
        )
    }

    @Test
    fun testAddingPost() {
        val resultId = 1u
        assertEquals(resultId, WallService.addPost(makeMock()).id)
    }

    @Test
    fun testSuccessfullUpdatingPost() {
        val post = makeMock()
        WallService.addPost(post)

        val newPost = post.copy(id = 1u, views = 100u)
        val update = WallService.updatePost(newPost)

        assertEquals(true, update)
    }

    @Test
    fun testFailedUpdatingPost() {
        val post = makeMock(100u)
        WallService.addPost(post)
        val newPost = makeMock()
        val update = WallService.updatePost(newPost)
        assertEquals(false, update)
    }

    @Test
    fun testAddingComment() {
        val addedPost = WallService.addPost(makeMock())
        val comment = Comment(1234u, 2u, "Love this")
        val addedComment = WallService.createComment(
            addedPost.id,
            comment
        )
        assertEquals(addedPost.comments.last(), addedComment.id)
    }

    @Test(expected = PostNotFoundException::class)
    fun testFailedAddingComment() {
        val comment = Comment(1234u, 2u, "Love this")
        val addedComment = WallService.createComment(
            100500u,
            comment
        )
    }

    @Test
    fun testReportComment() {
        val addedPost = WallService.addPost(makeMock())
        val comment = Comment(999u, 2u, "Love this")
        val addedComment = WallService.createComment(
            addedPost.id,
            comment
        )
        val report = WallService.report(999u, ReportReason.SPAM)
        assertEquals(ReportReason.SPAM, report.reason)
    }

    @Test(expected = PostNotFoundException::class)
    fun testFailedAddingReport() {
        val addedPost = WallService.addPost(makeMock())
        val comment = Comment(333u, 2u, "Love this")
        val addedComment = WallService.createComment(
            addedPost.id,
            comment
        )
        val report = WallService.report(999u, ReportReason.SPAM)
    }

    @Test(expected = ReasonIsNotProvided::class)
    fun testFailedReasonReport() {
        val addedPost = WallService.addPost(makeMock())
        val comment = Comment(333u, 2u, "Love this")
        val addedComment = WallService.createComment(
            addedPost.id,
            comment
        )
        val report = WallService.report(333u, null)
    }


    private fun mockNote(): Note {
        return Note("Amazing note", "Beautiful text ^^", NotePrivacy.ALL)
    }

    @Test
    fun testAddingNote() {
        val note = mockNote()
        val noteId = NoteService.add(note)

        assertEquals(1u, noteId)
    }

    @Test
    fun testAddingNoteComment() {
        val note = mockNote()
        val noteId = NoteService.add(note)
        val comment = NoteComment(noteId, "Oh, I LOVE IT")
        val commentId = NoteService.createComment(comment)

        assertEquals(1u, commentId)
    }

    @Test
    fun testDeletingNote() {
        val note = mockNote()
        val noteId = NoteService.add(note)
        NoteService.delete(noteId)
        val deletedNote = NoteService.getById(noteId)
        assertEquals(null, deletedNote)
    }

    @Test
    fun testDeletingComment() {
        val note = mockNote()
        val noteId = NoteService.add(note)
        val comment = NoteComment(noteId, "Oh, I LOVE IT")
        val commentId = NoteService.createComment(comment)
        NoteService.deleteComment(commentId)
        val deletedComment = NoteService.getComments(noteId).firstOrNull { it.id == commentId }
        assertEquals(null, deletedComment)
    }

    @Test
    fun testRestoringComment() {
        val note = mockNote()
        val noteId = NoteService.add(note)
        val comment = NoteComment(noteId, "Oh, I LOVE IT")
        val commentId = NoteService.createComment(comment)
        NoteService.deleteComment(commentId)
        NoteService.restoreComment(commentId)
        val restoredComment = NoteService.getComments(noteId).firstOrNull { it.id == commentId }
        assertEquals(false, restoredComment?.isDeleted)
    }

    @Test
    fun testUpdateNote() {
        val note = mockNote()
        val noteId = NoteService.add(note)
        val updatedNote = note.copy(title = "AWESOME EVEMT", text = "i forgot what i supposed to write")
        updatedNote.id = noteId
        val updateSuccess = NoteService.edit(updatedNote)

        assertEquals(1u, updateSuccess)
        val updated = NoteService.getById(noteId)
        assertEquals("AWESOME EVEMT", updated?.title)
        assertEquals("i forgot what i supposed to write", updated?.text)
    }

    @Test
    fun testUpdateComment() {
        val note = mockNote()
        val noteId = NoteService.add(note)
        val comment = NoteComment(noteId, "Oh, I LOVE IT")
        val commentId = NoteService.createComment(comment)

        val updatedComment = comment.copy(message = "It happens to me every time")
        updatedComment.id = commentId
        val updateSuccess = NoteService.editComment(updatedComment)

        assertEquals(1u, updateSuccess)
        val updated = NoteService.getComments(noteId).firstOrNull { it.id == commentId }
        assertEquals("It happens to me every time", updated?.message)
    }

    @Test(expected = ItemIsDeleted::class)
    fun testUpdatingDeletedNote() {
        val note = mockNote()
        val noteId = NoteService.add(note)
        NoteService.delete(noteId)
        val updatedNote = note.copy(title = "AWESOME EVEMT", text = "i forgot what i supposed to write")
        updatedNote.id = noteId
        NoteService.edit(updatedNote)
    }

    @Test
    fun createConversation() {
        val conversation = MessageService.createConversation(1u)
        assertEquals(1u, conversation.personId)
    }

    @Test
    fun sendMessage() {
        MessageService.sendMessage(1u, "Hello, I'm using WhatsApp")
        assertEquals(
            "Hello, I'm using WhatsApp",
            MessageService.getMessages(1u, 1u)[0].text)
    }

    @Test
    fun testDeleteMessage() {
        MessageService.sendMessage(1u, "Hello, I'm using WhatsApp")
        MessageService.sendMessage(1u, "message to delete")
        assertEquals(2, MessageService.getMessages(1u, 10u).count())
        val conv = MessageService.getAllConversations().first()
        val message = MessageService.getMessages(1u, 1u)[0]
        MessageService.deleteMessage(message.id, conv.id)
        assertEquals(1, MessageService.getMessages(1u, 1u).count())
    }

    @Test
    fun testClearConversation() {
        MessageService.sendMessage(1u, "Hello, I'm using WhatsApp")
        assertEquals(1, MessageService.getAllConversations().size)

        MessageService.clearConversation(1u)
        assertEquals(0, MessageService.getAllConversations().size)
    }

    @Test
    fun testGetLastMessages() {
        MessageService.sendMessage(1u, "1")
        MessageService.sendMessage(2u, "2")
        val lastMessages = MessageService.getLastMessages()
        assertEquals(2, lastMessages.size)
        assertEquals("1", lastMessages[0])
        assertEquals("2", lastMessages[1])
    }

    @Test
    fun testGetUnreadConversations() {
        MessageService.sendMessage(1u, "Hello, I'm using WhatsApp")
        val unreadCount = MessageService.getInreadConversations()
        assertEquals(1u, unreadCount)
        MessageService.getMessages(1u, 1u)
        val unreadCountAfter = MessageService.getInreadConversations()
        assertEquals(0u, unreadCountAfter)
    }
}