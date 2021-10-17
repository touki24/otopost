package com.touki.otopost.presentation.post

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.touki.otopost.R
import com.touki.otopost.core.post.model.Post
import com.touki.otopost.databinding.FragmentPostBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PostFragment : Fragment() {

    private val binding: FragmentPostBinding by viewBinding(createMethod = CreateMethod.INFLATE)
    private val viewModel: PostViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchPosts()
        viewModel.posts.observe(viewLifecycleOwner, {
            it.forEach { post ->
                Log.d("PostFrag", post.toString())
            }
        })
    }
}