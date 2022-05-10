package ru.netology.nmedia.data.impl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.countFormat
import kotlin.properties.Delegates

typealias OnPostClicked = (Post) -> Unit

internal class PostsAdapter(
    private val onLikeClicked: OnPostClicked,
    private val onShareClicked: OnPostClicked,
    private val onViewClicked: OnPostClicked
   ) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {

    var posts: List<Post> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class ViewHolder(
        private val binding: PostBinding

    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) = with(binding) {
            author.text = post.author
            content.text = post.content
            published.text = post.published
            likesCount.text = countFormat(post.likes)
            sharesCount.text = countFormat(post.shares)
            viewsCount.text = countFormat(post.views)
            likesButton.setImageResource(getLikeIconResId(post.likedByMe))
            likesButton.setOnClickListener {
                onLikeClicked(post)
            }
            shareButton.setOnClickListener {
                onShareClicked(post)
            }
            viewsButton.setOnClickListener {
                onViewClicked(post)
            }

        }

        @DrawableRes
        private fun getLikeIconResId(liked: Boolean) =
            if (liked) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24dp
    }

    private object DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
            oldItem == newItem
    }

}