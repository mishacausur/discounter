package ru.netology

import java.util.function.BiPredicate

data class Post(
    val id: UInt,
    val ownerId: UInt,
    val date: Int,
    val text: String,
    val views: UInt?,
    val markedAsAds: Boolean?,
    val likes: UInt,
    val canLike: Boolean,
    val userLiked: Boolean,
    val canPublish: Boolean,
    var comments: Array<UInt>,
    var attachment: Array<Attachment>
)

data class Comment(
    val id: UInt,
    val fromId: UInt,
    var text: String
)

object WallService {
    var id: UInt = 0u
    var posts = emptyArray<Post>()
    var comments = emptyArray<Comment>()
    var reports = emptyArray<Report>()

    fun addPost(post: Post): Post {
        id += 1u
        val _post = post.copy(id = id)
        posts += _post
        return posts.last()
    }

    fun updatePost(post: Post): Boolean {
        var result = false
        for ((index, _post) in posts.withIndex()) {
            if (_post.id == post.id) {
                posts[index] = post
                result = true
            }
        }
       return result
    }

    fun createComment(postId: UInt, comment: Comment): Comment {
        for ((index, _post) in posts.withIndex()) {
            if (_post.id == postId) {
                comments += comment
                posts[index].comments += comment.id
                return comment
            }
        }
        throw PostNotFoundException("Пост с ID $postId не найден")
    }

    fun report(commentId: UInt, reason: ReportReason?): Report {

        if (reason == null) {
            throw ReasonIsNotProvided("Не указана причина жалобы")
        }

        for (comment in comments ) {
            if (comment.id == commentId) {
                val report = Report(commentId, reason)
                reports += report
                return report
            }
        }
        throw PostNotFoundException("комментарий с ID $commentId не найден")
    }

    fun reset() {
        id = 0u
        posts = emptyArray()
    }
}

fun main() {

}
