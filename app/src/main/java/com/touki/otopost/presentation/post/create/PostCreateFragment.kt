package com.touki.otopost.presentation.post.create

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.touki.otopost.R
import com.touki.otopost.common.extension.showMessage
import com.touki.otopost.databinding.FragmentPostCreateBinding
import com.touki.otopost.util.extension.hideSoftInput
import com.touki.otopost.util.extension.navigateSafe
import com.touki.otopost.util.extension.setSupportActionBar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PostCreateFragment : Fragment() {

    private val binding: FragmentPostCreateBinding by viewBinding(createMethod = CreateMethod.INFLATE)
    private val viewModel: PostCreateViewModel by sharedViewModel()

    private val postTitleTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            binding.postTitle.error = null
        }

        override fun afterTextChanged(s: Editable?) {

        }
    }

    private val postContentTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            binding.postContent.error = null
        }

        override fun afterTextChanged(s: Editable?) {

        }
    }

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
                handleGoBack()
            }
        })
        setSupportActionBar(binding.toolbar).setNavigationOnClickListener {
            handleGoBack()
        }
        binding.toolbarButtonClear.setOnClickListener {
            handleClearForm()
        }

        setCreatedPostObserver()
        setErrorObserver()
        binding.buttonSubmit.setOnClickListener {
            handleCreatePost()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.postTitle.editText?.addTextChangedListener(postTitleTextWatcher)
        binding.postContent.editText?.addTextChangedListener(postContentTextWatcher)
    }

    override fun onPause() {
        super.onPause()
        binding.postTitle.editText?.removeTextChangedListener(postTitleTextWatcher)
        binding.postContent.editText?.removeTextChangedListener(postContentTextWatcher)
        activity?.viewModelStore?.clear()
    }

    private fun clearForm() {
        binding.postTitle.editText?.setText("")
        binding.postContent.editText?.setText("")
    }

    private fun handleClearForm() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.label_confirmation))
            .setMessage(resources.getString(R.string.warning_clear_form))
            .setCancelable(false)
            .setNegativeButton(resources.getString(R.string.label_decline)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.label_accept)) { dialog, _ ->
                dialog.dismiss()
                clearForm()
            }
            .show()
    }

    private fun handleGoBack() {
        hideSoftInput()
        val title = binding.postTitle.editText?.text.toString().trim()
        val content = binding.postContent.editText?.text.toString().trim()

        if (title.isNotBlank() or content.isNotBlank()) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.label_confirmation))
                .setMessage(resources.getString(R.string.warning_back_confirmation))
                .setCancelable(false)
                .setNegativeButton(resources.getString(R.string.label_decline)) { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton(resources.getString(R.string.label_accept)) { dialog, _ ->
                    goBack()
                    dialog.dismiss()
                }
                .show()

            return
        }

        goBack()
    }

    private fun handleCreatePost() {
        val title = binding.postTitle.editText?.text.toString().trim()
        val content = binding.postContent.editText?.text.toString().trim()

        if (title.isBlank()) {
            binding.postTitle.error = resources.getString(R.string.error_title_empty)
            return
        }

        val titleMinimalLength = 4
        if (title.length < titleMinimalLength) {
            binding.postTitle.error = resources.getString(R.string.error_title_too_short, titleMinimalLength)
            return
        }

        if (content.isBlank()) {
            binding.postContent.error = resources.getString(R.string.error_content_empty)
            return
        }

        val contentMinimalLength = 10
        if (content.length < contentMinimalLength) {
            binding.postContent.error = resources.getString(R.string.error_content_too_short, contentMinimalLength)
            return
        }

        startLoading()
        viewModel.createPost(title, content)
    }

    private fun handleCreatePostSuccess() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.label_information))
            .setMessage(resources.getString(R.string.info_create_post_success))
            .setCancelable(false)
            .setNegativeButton(resources.getString(R.string.label_back_to_posts)) { dialog, _ ->
                dialog.dismiss()
                clearForm()
                handleGoBack()
            }
            .setPositiveButton(resources.getString(R.string.label_create_post_again)) { dialog, _ ->
                dialog.dismiss()
                clearForm()
            }
            .show()
    }

    private fun startLoading() {
        hideSoftInput()
        binding.buttonSubmit.startAnimation()
    }

    private fun stopLoading() {
        binding.buttonSubmit.revertAnimation()
    }

    private fun goBack() {
        val action = PostCreateFragmentDirections.actionPostCreateFragmentToPostFragment()
        findNavController().navigateSafe(R.id.postCreateFragment, action)
    }

    private fun setCreatedPostObserver() {
        viewModel.createdPost.observe(viewLifecycleOwner, { post ->
            Log.d("TAG", "post: $post created successfully")
            stopLoading()
            handleCreatePostSuccess()
        })
    }

    private fun setErrorObserver() {
        viewModel.error.observe(viewLifecycleOwner, { message ->
            Log.d("", "setupErrorObserver: $message")
            stopLoading()
            showMessage(message)
        })
    }

}