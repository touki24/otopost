package com.touki.otopost.presentation.post.update

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.touki.otopost.presentation.post.create.PostCreateFragmentDirections
import com.touki.otopost.util.extension.hideSoftInput
import com.touki.otopost.util.extension.navigateSafe
import com.touki.otopost.util.extension.setSupportActionBar

class PostUpdateFragment : Fragment() {

    private val binding: FragmentPostUpdateBinding by viewBinding(createMethod = CreateMethod.INFLATE)
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
            showMessage("submit")
        }

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

    private fun handleClearForm() {
        val title = binding.postTitle.editText?.text.toString()
        val content = binding.postContent.editText?.text.toString()

        if (title.isNotBlank() or content.isNotBlank()) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.label_confirmation))
                .setMessage(resources.getString(R.string.warning_clear_form))
                .setNegativeButton(resources.getString(R.string.label_decline)) { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton(resources.getString(R.string.label_accept)) { dialog, _ ->
                    dialog.dismiss()
                    clearForm()
                }
                .show()
        }
    }

    private fun handleGoBack() {
        hideSoftInput()
        val title = binding.postTitle.editText?.text.toString()
        val content = binding.postContent.editText?.text.toString()

        if ((title != args.postTitle) or (content != args.postContent)) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.label_confirmation))
                .setMessage(resources.getString(R.string.warning_post_update_not_submited))
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
}