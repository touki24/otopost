package com.touki.otopost.presentation.post.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.touki.otopost.common.extension.showMessage
import com.touki.otopost.databinding.FragmentPostDetailBinding
import com.touki.otopost.presentation.post.PostViewModel
import com.touki.otopost.presentation.post.adapter.PostRecyclerAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PostDetailFragment : Fragment() {

    companion object {
        private const val TAG = "POSTD-FRAG"
    }
    private val binding: FragmentPostDetailBinding by viewBinding(createMethod = CreateMethod.INFLATE)
    private val viewModel: PostViewModel by sharedViewModel()
    private val adapter by lazy {
        PostRecyclerAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPostsObserver()
        setupErrorObserver()
        fetchPosts()
    }

    private fun fetchPosts() {
        binding.progressCircular.visibility = View.VISIBLE
        viewModel.fetchPosts()
    }

    private fun setupPostsObserver() {
        viewModel.posts.observe(viewLifecycleOwner, { posts ->
            binding.progressCircular.visibility = View.GONE
            adapter.setPosts(posts = posts)
            posts.forEach { post ->
                Log.d(TAG, post.toString())
            }
        })
    }

    private fun setupErrorObserver() {
        viewModel.error.observe(viewLifecycleOwner, {
            binding.progressCircular.visibility = View.GONE
            Log.d(TAG, "setupErrorObserver: $it")
            showMessage(it)
        })
    }
}