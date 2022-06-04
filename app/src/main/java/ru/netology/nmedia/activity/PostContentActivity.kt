package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.PostContentActivityBinding
import ru.netology.nmedia.util.showKeyboard
import ru.netology.nmedia.viewModel.PostViewModel

class PostContentActivity : AppCompatActivity() {


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        val binding = PostContentActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var editPost = intent.getStringExtra(Intent.EXTRA_TEXT)
        binding.edit.requestFocus()
        binding.edit.showKeyboard()
        if (editPost != null) {
            binding.undoButton.visibility = View.VISIBLE
            binding.edit.setText(editPost)
        }


        binding.undoButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }

        binding.ok.setOnClickListener {
            val intent = Intent()
            val text = binding.edit.text
            if (text.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val content = text.toString()
                intent.putExtra(RESULT_KEY, content)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }
    }

    object ResultContract : ActivityResultContract<String?, String?>() {

        override fun createIntent(context: Context, input: String?) =
            Intent(context, PostContentActivity::class.java)
                .putExtra(Intent.EXTRA_TEXT, input)


        override fun parseResult(resultCode: Int, intent: Intent?) =
            if (resultCode == Activity.RESULT_OK) {
                intent?.getStringExtra(RESULT_KEY)
            } else null
    }


    private companion object {
        private const val RESULT_KEY = "postNewContent"
    }
}