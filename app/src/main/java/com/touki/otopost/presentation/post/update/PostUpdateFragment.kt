package com.touki.otopost.presentation.post.update

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.touki.otopost.R
import com.touki.otopost.common.extension.showMessage
import com.touki.otopost.databinding.FragmentPostUpdateBinding
import com.touki.otopost.util.extension.hideSoftInput
import com.touki.otopost.util.extension.navigateSafe
import com.touki.otopost.util.extension.setSupportActionBar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PostUpdateFragment : Fragment() {

    private val binding: FragmentPostUpdateBinding by viewBinding(createMethod = CreateMethod.INFLATE)
    private val viewModel: PostUpdateViewModel by sharedViewModel()
    private val args: PostUpdateFragmentArgs by navArgs()

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
        binding.buttonSubmit.setOnClickListener {
            handleUpdatePost()
        }

        setUpdatedPostObserver()
        setErrorObserver()
        fillForm()
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

    private fun fillForm() {
        binding.postTitle.editText?.setText(args.postTitle)
        binding.postContent.editText?.setText(args.postContent)
    }

    private fun handleUpdatePost() {
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

        if ((title == args.postTitle) and (content == args.postContent)) {
            showMessage(resources.getString(R.string.error_update_post_the_same))
            return
        }

        startLoading()
        viewModel.updatePost(args.postId, title, content)
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

        if ((title != args.postTitle) or (content != args.postContent)) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.label_confirmation))
                .setMessage(resources.getString(R.string.warning_post_update_not_submited))
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

    private fun goBack() {
        val action = PostUpdateFragmentDirections.actionPostUpdateFragmentToPostDetailFragment(args.postId)
        findNavController().navigateSafe(R.id.postUpdateFragment, action)
    }

    private fun startLoading() {
        hideSoftInput()
        binding.buttonSubmit.startAnimation()
    }

    private fun stopLoading() {
        binding.buttonSubmit.revertAnimation()
    }

    private fun setUpdatedPostObserver() {
        viewModel.updatedPost.observe(viewLifecycleOwner, { post ->
            Log.d("TAG", "post: $post created successfully")
            stopLoading()
            goBack()
            showMessage(resources.getString(R.string.info_update_post_success))
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