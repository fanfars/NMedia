package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post

class InMemoryPostRepository : PostRepository {

    private val posts
        get() = checkNotNull(data.value) { "Data value should not be null" }

    override val data = MutableLiveData(
        List(100) { index ->
            Post(
                id = index + 1L,
                author = "Netology",
                content = "Some random content $index",
                published = "07/05/2022",
                likes = 9999,
                shares = 99999,
                views = 150
            )
        }
    )

    override fun like(postId: Long) {
        data.value = posts.map {
            if (it.id != postId) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )
        }
    }

    override fun share(postId: Long) {
        data.value = posts.map {
            if (it.id != postId) it else it.copy(
                shares = it.shares + 1
            )
        }
    }

    override fun view(postId: Long) {
        data.value = posts.map {
            if (it.id != postId) it else it.copy(
                views = it.views + 1
            )
        }
    }


}