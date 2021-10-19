package com.touki.otopost.framework.core.post.repo

import com.touki.otopost.common.result.CommonResult
import com.touki.otopost.common.result.ResultWithCache
import com.touki.otopost.core.post.model.Post
import com.touki.otopost.core.post.repo.PostRepository
import com.touki.otopost.core.post.source.*

internal class PostRepositoryImpl(
    private val apiFetchPosts: ApiFetchPosts,
    private val apiFetchPost: ApiFetchPost,
    private val apiDeletePost: ApiDeletePost,
    private val apiCreatePost: ApiCreatePost,
    private val apiUpdatePost: ApiUpdatePost
): PostRepository {
    override suspend fun fetchPosts(): ResultWithCache<List<Post>> {
        val posts = apiFetchPosts().fold(
            success = { postList ->
                postList
            },
            failure = { error, cache ->
                return ResultWithCache.Failure(error, cache)
            }
        )
        return ResultWithCache.Success(posts)
    }

    override suspend fun fetchPost(postId: Int): CommonResult<Post> {
        val post = apiFetchPost(postId).fold(
            success = { post ->
                post
            },
            failure = {
                return CommonResult.Failure(it)
            }
        )

        return CommonResult.Success(post)
    }

    override suspend fun deletePost(postId: Int): CommonResult<Post> {
        val post = apiDeletePost(postId).fold(
            success = { post ->
                post
            },
            failure = {
                return CommonResult.Failure(it)
            }
        )

        return CommonResult.Success(post)
    }

    override suspend fun createPost(title: String, content: String): CommonResult<Post> {
        val post = apiCreatePost(title, content).fold(
            success = { post ->
                post
            },
            failure = {
                return CommonResult.Failure(it)
            }
        )

        return CommonResult.Success(post)
    }

    override suspend fun updatePost(id: Int, title: String, content: String): CommonResult<Post> {
        val post = apiUpdatePost(id, title, content).fold(
            success = { post ->
                post
            },
            failure = {
                return CommonResult.Failure(it)
            }
        )

        return CommonResult.Success(post)
    }
}