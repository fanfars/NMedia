package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostFragmentBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.countFormat


internal class PostsAdapter(
    private val interactionListener: PostInteractionListener
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostFragmentBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(
        private val binding: PostFragmentBinding,
        listener: PostInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.menu).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(post)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(post)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.likesButton.setOnClickListener { listener.onLikeClicked(post) }
            binding.shareButton.setOnClickListener { listener.onShareClicked(post) }
            binding.viewsButton.setOnClickListener { listener.onViewClicked(post) }
            binding.content.setOnClickListener { listener.onPostClicked(post) }
            binding.author.setOnClickListener { listener.onPostClicked(post) }
            binding.avatar.setOnClickListener { listener.onPostClicked(post) }
            binding.published.setOnClickListener { listener.onPostClicked(post) }
            binding.videoCover.setOnClickListener { listener.onPlayVideoClicked(post) }
            binding.videoContent.setOnClickListener { listener.onPlayVideoClicked(post) }
            binding.menu.setOnClickListener { popupMenu.show() }
        }

        fun bind(post: Post) {
            this.post = post

            with(binding) {
                author.text = post.author
                content.text = post.content
                published.text = post.published
                likesButton.text = countFormat(post.likes)
                shareButton.text = countFormat(post.shares)
                viewsButton.text = countFormat(post.views)
                likesButton.isChecked = post.likedByMe
                videoContent.text = "VIDEO"
                if (post.videoContent != null) videoGroup.visibility =
                    View.VISIBLE else videoGroup.visibility = View.GONE
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
            oldItem == newItem
    }

}