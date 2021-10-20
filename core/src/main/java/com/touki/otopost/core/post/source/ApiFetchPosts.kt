package com.touki.otopost.core.post.source

import com.touki.otopost.common.result.CommonResult
import com.touki.otopost.common.result.ResultWithCache
import com.touki.otopost.core.post.model.Post

interface ApiFetchPosts {
    suspend operator fun invoke(): ResultWithCache<List<Post>>
}