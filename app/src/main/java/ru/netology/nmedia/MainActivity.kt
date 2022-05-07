package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.DrawableRes
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.countFormat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 0L,
            author = resources.getString(R.string.author),
            content = resources.getString(R.string.content),
            published = resources.getString(R.string.published),
            likes = resources.getString(R.string.likesCount).toInt(),
            shares = resources.getString(R.string.sharesCount).toInt(),
            views = resources.getString(R.string.viewsCount).toInt()
        )

        binding.render(post)


    }

    private fun ActivityMainBinding.render(post: Post) {
        author.text = post.author
        content.text = post.content
        published.text = post.published

        likesButton?.setOnClickListener {
            post.likedByMe = !post.likedByMe
            likesButton.setImageResource(
                getLikeIconResId(post.likedByMe)
            )
            likesCount.text = countFormat(if (post.likedByMe) (post.likes + 1) else (post.likes))
        }

        shareButton?.setOnClickListener {
            sharesCount.text = countFormat(post.shares++)
        }


    }

    @DrawableRes
    private fun getLikeIconResId(liked: Boolean) =
        if (liked) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24dp


}