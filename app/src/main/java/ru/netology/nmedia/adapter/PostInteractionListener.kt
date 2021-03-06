package ru.netology.nmedia.adapter

import ru.netology.nmedia.dto.Post


interface PostInteractionListener {

    fun onLikeClicked(post: Post)
    fun onShareClicked(post: Post)
    fun onViewClicked(post: Post)
    fun onRemoveClicked(post: Post)
    fun onEditClicked(post: Post)
    fun onPostClicked(post: Post)
    fun onUndoClicked()
    fun onPlayVideoClicked(post: Post)

}