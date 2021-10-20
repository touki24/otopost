package com.touki.otopost.core.post.source

import com.touki.otopost.common.result.CommonResult
import com.touki.otopost.core.post.model.Post

interface DatabaseSavePosts {
    suspend operator fun invoke(posts: List<Post>): CommonResult<Long>
}