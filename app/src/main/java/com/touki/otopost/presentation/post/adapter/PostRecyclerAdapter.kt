package com.touki.otopost.presentation.post.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.touki.otopost.core.post.model.Post
import com.touki.otopost.databinding.ItemPostBinding

class PostRecyclerAdapter: RecyclerView.Adapter<PostRecyclerAdapter.Holder>() {

    interface ItemClickListener {
        fun onClick(post: Post)
    }

    private var posts: List<Post> = listOf()
    private var listener: ItemClickListener? = null

    fun setPosts(posts: List<Post>) {
        this.posts = posts
        notifyDataSetChanged()
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
            binding.container.setOnClickListener {
                listener?.onClick(post)
            }
        }
    }
}