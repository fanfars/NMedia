package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.countFormat
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                content.text = post.content
                published.text = post.published
                likesCount.text = countFormat(post.likes)
                sharesCount.text = countFormat(post.shares)
                viewsCount.text = countFormat(post.views)
                likesButton.setImageResource(getLikeIconResId(post.likedByMe))
            }
        }

        binding.likesButton.setOnClickListener {
            viewModel.onLikeClicked()
        }

        binding.shareButton.setOnClickListener {
            viewModel.onShareClicked()
        }

        binding.viewsButton.setOnClickListener {
            viewModel.onViewClicked()
        }
    }
}


@DrawableRes
private fun getLikeIconResId(liked: Boolean) =
    if (liked) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24dp


