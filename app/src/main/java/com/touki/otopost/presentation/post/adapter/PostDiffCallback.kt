package com.touki.otopost.presentation.post.adapter

import androidx.recyclerview.widget.DiffUtil
import com.touki.otopost.core.post.model.Post

class PostDiffCallback(private val oldPosts: List<Post>, private val newPosts: List<Post>): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldPosts.size
    }

    override fun getNewListSize(): Int {
        return newPosts.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldPosts[oldItemPosition].id == newPosts[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPost = oldPosts[oldItemPosition]
        val newPost = newPosts[newItemPosition]

        val isTitleTheSame = oldPost.title == newPost.title
        val isContentTheSame = oldPost.content == newPost.content
        val isCreatedAtTheSame = oldPost.createdAt == newPost.createdAt
        val isUpdatedAtTheSame = oldPost.updatedAt == newPost.updatedAt
        val isPublishedAtTheSame = oldPost.publishedAt == newPost.publishedAt

        return isTitleTheSame && isContentTheSame && isCreatedAtTheSame && isUpdatedAtTheSame && isPublishedAtTheSame
    }
}