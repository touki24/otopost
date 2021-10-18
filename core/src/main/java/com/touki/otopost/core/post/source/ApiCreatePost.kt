package com.touki.otopost.core.post.source

import com.touki.otopost.common.result.CommonResult
import com.touki.otopost.core.post.model.Post

interface ApiCreatePost {
    suspend operator fun invoke(title: String, content: String): CommonResult<Post>
}