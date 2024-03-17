package com.example.myprapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myprapp.R
import com.example.myprapp.adapter.OnInteractionListener
import com.example.myprapp.adapter.PostsAdapter
import com.example.myprapp.databinding.ActivityMainBinding
import com.example.myprapp.dto.Post
import com.example.myprapp.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        run {
//            val preferences = getPreferences(Context.MODE_PRIVATE)
//            preferences.edit().apply {
//                putString("key", "value") // putX
//                commit() // commit - синхронно, apply - асинхронно
//            }
//        }
//
//        run {
//            getPreferences(Context.MODE_PRIVATE)
//                .getString("key", "no value")?.let {
//                    Snackbar.make(binding.root, it, BaseTransientBottomBar.LENGTH_INDEFINITE)
//                        .show()
//                }
//        }

        val viewModel: PostViewModel by viewModels()

        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }

                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
            }
        })
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        val newPostLauncher = registerForActivityResult(NewPostResultContract()) { result ->
            result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            viewModel.save()
        }

        binding.fab.setOnClickListener {
            newPostLauncher.launch()
        }
    }
}