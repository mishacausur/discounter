import org.junit.Test
import junit.framework.TestCase.assertEquals
import org.junit.Before
import ru.netology.*

class MainKtTest {

    @Before
    fun resetWallService() {
        WallService.reset()
    }

    fun mockAttachment(): Array<Attachment> {
        val photo = Photo(1u, 1u, 1u, null
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
        val comment = Comment( 1234u, 2u, "Love this")
        val addedComment = WallService.createComment(
            addedPost.id,
            comment
        )
        assertEquals(addedPost.comments.last(), addedComment.id)
    }

    @Test(expected = PostNotFoundException::class)
    fun testFailedAddingComment() {
        val comment = Comment( 1234u, 2u, "Love this")
        val addedComment = WallService.createComment(
            100500u,
            comment
        )
    }

    @Test
    fun testReportComment() {
        val addedPost = WallService.addPost(makeMock())
        val comment = Comment( 999u, 2u, "Love this")
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
        val comment = Comment( 333u, 2u, "Love this")
        val addedComment = WallService.createComment(
            addedPost.id,
            comment
        )
        val report = WallService.report(999u, ReportReason.SPAM)
    }

    @Test(expected = ReasonIsNotProvided::class)
    fun testFailedReasonReport() {
        val addedPost = WallService.addPost(makeMock())
        val comment = Comment( 333u, 2u, "Love this")
        val addedComment = WallService.createComment(
            addedPost.id,
            comment
        )
        val report = WallService.report(333u, null)
    }
}