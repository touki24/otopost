package com.touki.otopost.core.post.repo

import com.touki.otopost.common.result.CommonResult
import com.touki.otopost.common.result.ResultWithCache
import com.touki.otopost.core.post.model.Post

interface PostRepository {
    suspend fun fetchPosts(): ResultWithCache<List<Post>>

    suspend fun fetchPost(postId: Int): CommonResult<Post>

    suspend fun deletePost(postId: Int): CommonResult<Post>

    suspend fun createPost(title: String, content: String): CommonResult<Post>

    suspend fun updatePost(id: Int, title: String, content: String): CommonResult<Post>
}