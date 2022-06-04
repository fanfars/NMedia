package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.FilePostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository
import ru.netology.nmedia.data.impl.SharedPrefsPostRepository
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.PostVideo
import ru.netology.nmedia.util.SingleLiveEvent
import java.lang.Appendable

class PostViewModel(
    application: Application
) : AndroidViewModel(application), PostInteractionListener {

    private val repository: PostRepository = FilePostRepository(application)

    val data by repository::data

    val sharePostContent = SingleLiveEvent<String>()
    val videoPlay = SingleLiveEvent<String>()
    val navigateToPostContentScreenEvent = SingleLiveEvent<Unit>()
    val navigateToPostContentEditEvent = SingleLiveEvent<Unit>()

    val currentPost = MutableLiveData<Post?>(null)

    fun onSaveButtonClicked(content: String) {
        if (content.isBlank()) return
        val post = currentPost.value?.copy(
            content = content
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Me",
            content = content,
            published = "Today",
            postVideo = PostVideo(
                url = "https://www.youtube.com/watch?v=xOgT2qYAzds",
                title = "Онлайн-образование. Революция уже наступила?"
            )
        )
        repository.save(post)
        currentPost.value = null
    }

    fun onAddButtonClicked() {
        navigateToPostContentScreenEvent.call()
    }


    // region  PostInteractionListener

    override fun onLikeClicked(post: Post) = repository.like(post.id)

    override fun onShareClicked(post: Post) {
        sharePostContent.value = post.content

    }

    override fun onViewClicked(post: Post) = repository.view(post.id)

    override fun onRemoveClicked(post: Post) = repository.delete(post.id)

    override fun onEditClicked(post: Post) {
        currentPost.value = post
        navigateToPostContentEditEvent.call()
    }

    override fun onUndoClicked() {
        currentPost.value = null
    }

    override fun onPlayVideoClicked(postVideo: PostVideo) {
        videoPlay.value = postVideo.url!!
    }

// endregion  PostInteractionListener
}