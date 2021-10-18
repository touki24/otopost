package com.touki.otopost.presentation.post

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.touki.otopost.R
import com.touki.otopost.common.extension.showMessage
import com.touki.otopost.databinding.FragmentPostBinding
import com.touki.otopost.presentation.post.adapter.PostRecyclerAdapter
import com.touki.otopost.util.BounceEdgeEffectFactory
import com.touki.otopost.util.extension.navigateSafe
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PostFragment : Fragment() {

    companion object {
        private const val TAG = "POST-FRAG"
    }
    private val binding: FragmentPostBinding by viewBinding(createMethod = CreateMethod.INFLATE)
    private val viewModel: PostViewModel by sharedViewModel()
    private val adapter by lazy {
        PostRecyclerAdapter()
    }

    private val bounceEdgeEffectFactory by lazy {
        BounceEdgeEffectFactory()
    }

    private val recyclerItemClickListener by lazy {
        object : PostRecyclerAdapter.ItemClickListener {
            override fun onClick(postId: Int) {
                Log.d(TAG, "onClick: $postId")
                val action = PostFragmentDirections.actionPostFragmentToPostDetailFragment(postId)
                findNavController().navigateSafe(R.id.postFragment, action)
            }
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
        setupPostsRecycler()
        setupPostsObserver()
        setupErrorObserver()
        binding.progressCircular.visibility = View.GONE
        fetchPosts()
    }


    override fun onPause() {
        super.onPause()
        binding.progressCircular.visibility = View.GONE
    }

    private fun fetchPosts() {
        if (binding.progressCircular.isVisible) {
            return
        }

        binding.progressCircular.visibility = View.VISIBLE
        viewModel.fetchPosts()
    }

    private fun setupPostsRecycler() {
        adapter.setItemClickListener(recyclerItemClickListener)
        binding.recyclerPost.adapter = adapter
        binding.recyclerPost.layoutManager = object: LinearLayoutManager(requireContext()) {
            override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
                val scrollRange = super.scrollVerticallyBy(dy, recycler, state)
                val overScroll = dy - scrollRange
                if (overScroll < -60) { // minus value means its top over scroll
                    fetchPosts()
                }
                return scrollRange
            }
        }
        binding.recyclerPost.edgeEffectFactory = bounceEdgeEffectFactory
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