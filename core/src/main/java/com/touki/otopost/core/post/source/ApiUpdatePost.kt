package com.touki.otopost.core.post.source

import com.touki.otopost.common.result.CommonResult
import com.touki.otopost.core.post.model.Post

interface ApiUpdatePost {
    suspend operator fun invoke(id: Int, title: String, content: String): CommonResult<Post>
}