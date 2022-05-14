package ru.netology.nmedia

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.hideKeyboard
import ru.netology.nmedia.util.showKeyboard
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(viewModel)
        binding.postRecyclerView.adapter = adapter

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)

        }

        binding.undoButton.setOnClickListener {
            viewModel.onUndoClicked()
        }

        binding.sendContentButton.setOnClickListener {
            with(binding.contentEditText) {
                val content = text.toString()
                viewModel.onSaveButtonClicked(content)
            }
        }

        viewModel.currentPost.observe(this) { currentPost ->
            with(binding) {
                val content = currentPost?.content
                contentEditText.setText(content)
                if (content != null) {
                    contentEditText.requestFocus()
                    contentEditText.showKeyboard()
                    editGroup.visibility = View.VISIBLE
                } else {
                    contentEditText.clearFocus()
                    contentEditText.hideKeyboard()
                    editGroup.visibility = View.GONE
                }

            }

        }

    }
}
