package com.touki.otopost.presentation.post.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.touki.otopost.R
import com.touki.otopost.common.extension.showMessage
import com.touki.otopost.core.post.model.Post
import com.touki.otopost.databinding.FragmentPostDetailBinding
import com.touki.otopost.util.extension.setSupportActionBar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PostDetailFragment : Fragment() {

    private val binding: FragmentPostDetailBinding by viewBinding(createMethod = CreateMethod.INFLATE)
    private val viewModel: PostDetailViewModel by sharedViewModel()
    private val args: PostDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarButtonLeft.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.toolbarButtonRight.setOnClickListener {
            deletePost()
        }
        Log.d("TAG", "onViewCreated: ${args.postId}")
        setupPostObserver()
        setupErrorObserver()
        fetchPost()
    }

    private fun deletePost() {
        Log.d("TAG", "deletePost: ${args.postId}")
    }

    private fun fetchPost() {
        binding.progressCircular.visibility = View.VISIBLE
        viewModel.fetchPost(args.postId)
    }

    private fun setupPostObserver() {
        viewModel.post.observe(viewLifecycleOwner, { post ->
            binding.progressCircular.visibility = View.GONE
            populatePost(post.title, post.content)
        })
    }

    private fun setupErrorObserver() {
        viewModel.error.observe(viewLifecycleOwner, {
            binding.progressCircular.visibility = View.GONE
            Log.d("", "setupErrorObserver: $it")
            showMessage(it)
        })
    }

    private fun populatePost(title: String, content: String) {
        binding.title.text = title
        binding.content.text = content
    }
}