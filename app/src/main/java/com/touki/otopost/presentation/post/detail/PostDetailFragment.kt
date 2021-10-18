package com.touki.otopost.presentation.post.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.touki.otopost.R
import com.touki.otopost.common.extension.showMessage
import com.touki.otopost.core.post.model.Post
import com.touki.otopost.databinding.FragmentPostDetailBinding
import com.touki.otopost.util.extension.navigateSafe
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
        activity?.onBackPressedDispatcher?.addCallback(requireActivity(), object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                goBack()
            }
        })
        binding.toolbarButtonLeft.setOnClickListener {
            goBack()
        }
        binding.toolbarButtonRight.setOnClickListener {
            deletePost()
        }
        Log.d("TAG", "onViewCreated: ${args.postId}")
        binding.progressCircular.visibility = View.GONE
        fetchPost()
    }

    override fun onResume() {
        super.onResume()
        viewModel.post.observe(viewLifecycleOwner, postObserver)
        viewModel.deletedPost.observe(viewLifecycleOwner, deletedPostObserver)
        viewModel.error.observe(viewLifecycleOwner, errorObserver)
    }

    override fun onPause() {
        super.onPause()
        viewModel.post.removeObserver(postObserver)
        viewModel.deletedPost.removeObserver(deletedPostObserver)
        viewModel.error.removeObserver(errorObserver)
        activity?.viewModelStore?.clear()
    }

    private fun goBack() {
        findNavController().navigateSafe(R.id.postDetailFragment, R.id.action_postDetailFragment_to_postFragment)
    }

    private val postObserver = Observer<Post> { post ->
        binding.progressCircular.visibility = View.GONE
        populatePost(post.title, post.content)
    }

    private val deletedPostObserver = Observer<Post> {
        binding.progressCircular.visibility = View.GONE
        Log.d("TAG", "setupDeletedPostObserver: backnya kepanggil")
        goBack()
    }

    private val errorObserver = Observer<String> { message ->
        binding.progressCircular.visibility = View.GONE
        Log.d("", "setupErrorObserver: $message")
        showMessage(message)
    }

    private fun deletePost() {
        if (binding.progressCircular.isVisible) {
            return
        }
        Log.d("TAG", "deletePost: ${args.postId}")
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.label_confirmation))
            .setMessage(resources.getString(R.string.warning_deleting_post))
            .setNegativeButton(resources.getString(R.string.label_decline)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.label_accept)) { dialog, _ ->
                viewModel.deletePost(args.postId)
                dialog.dismiss()
            }
            .show()
    }

    private fun fetchPost() {
        if (binding.progressCircular.isVisible) {
            return
        }
        Log.d("TAG", "fetchPost: ${args.postId}")
        binding.progressCircular.visibility = View.VISIBLE
        viewModel.fetchPost(args.postId)
    }

    private fun populatePost(title: String, content: String) {
        binding.title.text = title
        binding.content.text = content
    }
}