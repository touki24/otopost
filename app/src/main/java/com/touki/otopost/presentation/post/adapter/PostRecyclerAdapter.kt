package com.touki.otopost.presentation.post.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
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

    private fun getIdPositionZero(posts: List<Post>): Int {
        if (posts.isEmpty()) {
            return 0
        }
        return posts[0].id
    }

    fun setPosts(posts: List<Post>): Boolean { // return true if position zero is different
        val isNeedToScroll = getIdPositionZero(this.posts) != getIdPositionZero(posts)
        val diffCallback = PostDiffCallback(this.posts, posts)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.posts.clear()
        this.posts.addAll(posts)
        diffResult.dispatchUpdatesTo(this)
        return isNeedToScroll
    }

    fun setItemClickListener(listener: ItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return Holder(rootView, listener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    class Holder(itemView: View, private val listener: ItemClickListener?): RecyclerView.ViewHolder(itemView) {
        private val textTitle: MaterialTextView = itemView.findViewById(R.id.title)
        private val textContent: MaterialTextView = itemView.findViewById(R.id.content)
        private val textUpdatedAt: MaterialTextView = itemView.findViewById(R.id.updatedAt)
        private val container: MaterialCardView = itemView.findViewById(R.id.container)

        fun bind(post: Post) {
            textTitle.text = post.title
            textContent.text = post.content
            textUpdatedAt.text = itemView.context.resources.getString(R.string.info_updated_at, post.updatedAt.toString(DATE_TIME_POST))
            container.setOnClickListener {
                listener?.onClick(post.id)
            }
        }
    }
}