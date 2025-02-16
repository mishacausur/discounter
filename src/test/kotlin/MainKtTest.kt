import org.junit.Test
import junit.framework.TestCase.assertEquals
import org.junit.Before
import ru.netology.*

class MainKtTest {

    @Before
    fun resetWallService() {
        WallService.reset()
        NoteService.clear()
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
}