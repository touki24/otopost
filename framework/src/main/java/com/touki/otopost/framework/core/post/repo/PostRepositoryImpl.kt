package com.touki.otopost.framework.core.post.repo

import com.touki.otopost.common.result.CommonResult
import com.touki.otopost.core.post.model.Post
import com.touki.otopost.core.post.repo.PostRepository
import com.touki.otopost.core.post.source.ApiFetchPosts

internal class PostRepositoryImpl(private val apiFetchPosts: ApiFetchPosts): PostRepository {
    override suspend fun fetchPosts(): CommonResult<List<Post>> {
        val posts = apiFetchPosts().fold(
            success = { postList ->
                postList
            },
            failure = {
                return CommonResult.Failure(it)
            }
        )
        return CommonResult.Success(posts)
    }
}