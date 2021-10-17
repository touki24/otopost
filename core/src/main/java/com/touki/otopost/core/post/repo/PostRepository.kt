package com.touki.otopost.core.post.repo

import com.touki.otopost.common.result.CommonResult
import com.touki.otopost.core.post.model.Post

interface PostRepository {
    suspend fun fetchPosts(): CommonResult<List<Post>>
}