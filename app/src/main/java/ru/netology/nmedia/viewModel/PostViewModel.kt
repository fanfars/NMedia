package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.SQLiteRepository
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.SingleLiveEvent

class PostViewModel(
    application: Application
) : AndroidViewModel(application),
    PostInteractionListener {

    private val repository: PostRepository = SQLiteRepository(
        dao = AppDb.getInstance(
            context = application
        ).postDao
    )

    val data by repository::data

    val sharePostContent = SingleLiveEvent<String>()
    val videoPlay = SingleLiveEvent<String?>()
    val navigateToPostContentScreenEvent = SingleLiveEvent<String>()
    val navigateToPostScreenEvent = SingleLiveEvent<Long>()

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
            videoContent = "https://www.youtube.com/watch?v=xOgT2qYAzds"
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
        repository.share(post.id)

    }

    override fun onViewClicked(post: Post) = repository.view(post.id)

    override fun onRemoveClicked(post: Post) = repository.delete(post.id)

    override fun onEditClicked(post: Post) {
        currentPost.value = post
        navigateToPostContentScreenEvent.value = post.content
    }

    override fun onPostClicked(post: Post) {
        navigateToPostScreenEvent.value = post.id
    }

    override fun onUndoClicked() {
        currentPost.value = null
    }

    override fun onPlayVideoClicked(post: Post) {
        videoPlay.value = post.videoContent
    }

// endregion  PostInteractionListener
}