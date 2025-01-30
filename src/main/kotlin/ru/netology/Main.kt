package ru.netology

import java.util.function.BiPredicate

data class Post(
    val id: UInt,
    val ownerId: UInt,
    val date: Int,
    val text: String,
    val views: UInt,
    val markedAsAds: Boolean,
    val likes: UInt,
    val canLike: Boolean,
    val userLiked: Boolean,
    val canPublish: Boolean,
    val comments: Comments,
)

data class Comments(
    val count: UInt,
    val canPost: Boolean,
    val canClose: Boolean,
    val canOpen: Boolean
)

object Wall {
    var id: UInt = 0u
    private var posts = emptyArray<Post>()

    fun addPost(post: Post): Post {
        id += 1u
        val _post = post.copy(id = id)
        posts += _post
        return posts.last()
    }

    fun updatePost(post: Post): Boolean {
        for ((index, _post) in posts.withIndex()) {
            if (_post.id == post.id) {
                posts[index] = post
                return true
            }
        }
        return false
    }
}

fun main() {

}
