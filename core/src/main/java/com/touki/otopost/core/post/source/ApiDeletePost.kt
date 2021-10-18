package com.touki.otopost.core.post.source

import com.touki.otopost.common.result.CommonResult
import com.touki.otopost.core.post.model.Post

interface ApiDeletePost {
    suspend operator fun invoke(postId: Int): CommonResult<Post>
}