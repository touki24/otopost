package com.touki.otopost.presentation.post.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.touki.otopost.R
import com.touki.otopost.common.constant.DATE_TIME_POST
import com.touki.otopost.common.extension.toString
import com.touki.otopost.core.post.model.Post
import com.touki.otopost.databinding.ItemPostBinding

class PostRecyclerAdapter: RecyclerView.Adapter<PostRecyclerAdapter.Holder>() {

    interface ItemClickListener {
        fun onClick(postId: Int)
    }

    private var posts: MutableList<Post> = mutableListOf()
    private var listener: ItemClickListener? = null

    fun setPosts(posts: List<Post>) {
        val diffCallback = PostDiffCallback(this.posts, posts)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.posts.clear()
        this.posts.addAll(posts)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setItemClickListener(listener: ItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    class Holder(private val binding: ItemPostBinding, private val listener: ItemClickListener?): RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.title.text = post.title
            binding.content.text = post.content
            binding.updatedAt.text = binding.root.context.resources.getString(R.string.info_updated_at, post.updatedAt.toString(DATE_TIME_POST))
            binding.container.setOnClickListener {
                listener?.onClick(post.id)
            }
        }
    }
}