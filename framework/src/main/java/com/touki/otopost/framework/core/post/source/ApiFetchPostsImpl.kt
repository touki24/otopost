package com.touki.otopost.framework.core.post.source

import com.touki.otopost.common.result.CommonResult
import com.touki.otopost.core.post.model.Post
import com.touki.otopost.core.post.source.ApiFetchPosts

class ApiFetchPostsImpl: ApiFetchPosts {
    override suspend fun invoke(): CommonResult<List<Post>> {
        return CommonResult.Success(listOf())
    }
}