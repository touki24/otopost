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
        setSupportActionBar(binding.toolbar).setNavigationOnClickListener {
            goBack()
        }
        binding.toolbarButtonDelete.setOnClickListener {
            deletePost()
        }
        binding.toolbarButtonEdit.setOnClickListener {
            showMessage("ke edit")
        }

        setPostObserver()
        setDeletedPostObserver()
        setErrorObserver()
        Log.d("TAG", "onViewCreated: ${args.postId}")
        binding.progressCircular.visibility = View.GONE
        fetchPost()
    }

    override fun onPause() {
        super.onPause()
        activity?.viewModelStore?.clear()
    }

    private fun goBack() {
        val action = PostDetailFragmentDirections.actionPostDetailFragmentToPostFragment()
        findNavController().navigateSafe(R.id.postDetailFragment, action)
    }

    private fun setPostObserver() {
        viewModel.post.observe(viewLifecycleOwner, { post ->
            binding.progressCircular.visibility = View.GONE
            populatePost(post.title, post.content)
        })
    }

    private fun setDeletedPostObserver() {
        viewModel.deletedPost.observe(viewLifecycleOwner, {
            binding.progressCircular.visibility = View.GONE
            Log.d("TAG", "setupDeletedPostObserver: backnya kepanggil")
            goBack()
        })
    }

    private fun setErrorObserver() {
        viewModel.error.observe(viewLifecycleOwner, { message ->
            binding.progressCircular.visibility = View.GONE
            Log.d("", "setupErrorObserver: $message")
            showMessage(message)
        })
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