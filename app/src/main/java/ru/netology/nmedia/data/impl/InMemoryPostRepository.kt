package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.netology.nmedia.R
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post
import java.util.*

class InMemoryPostRepository : PostRepository {

    override val data = MutableLiveData(
        Post(
            id = 0L,
            author ="author" ,
            content = "content",
            published = "published",
            likes = 9999,
            shares = 99999,
            views = 150
        )
    )

    init {
        GlobalScope.launch(Dispatchers.Default) {
            while (true) {
                delay(10_000)
                val currentPost = checkNotNull(data.value) {
                    "Data value should not be null"
                }
                val newPost = currentPost.copy(
                    published = Date().toString()
                )
                data.postValue(newPost)
            }
        }
    }

    override fun like() {
        val currentPost = checkNotNull(data.value) { "Data value should not be null" }
        val likedPost = currentPost.copy(
            likedByMe = !currentPost.likedByMe,
            likes = if (currentPost.likedByMe) currentPost.likes - 1 else currentPost.likes + 1
        )
        data.value = likedPost
    }

    override fun share() {
        val currentPost = checkNotNull(data.value) { "Data value should not be null" }
        val sharedPost = currentPost.copy(
            shares = ++currentPost.shares
        )
        data.value = sharedPost
    }

    override fun view() {
        val currentPost = checkNotNull(data.value) { "Data value should not be null" }
        val viewedPost = currentPost.copy(
            views = ++currentPost.views
        )
        data.value = viewedPost
    }


}