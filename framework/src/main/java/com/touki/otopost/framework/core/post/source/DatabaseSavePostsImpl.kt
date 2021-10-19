package com.touki.otopost.framework.core.post.source

import com.touki.otopost.common.result.CommonResult
import com.touki.otopost.common.result.DatabaseError
import com.touki.otopost.core.post.model.Post
import com.touki.otopost.core.post.source.DatabaseSavePosts
import com.touki.otopost.framework.core.post.mapper.PostMapper

//TODO: not done yet
class DatabaseSavePostsImpl(private val postDao: PostDao): DatabaseSavePosts {
    private val mapper by lazy {
        PostMapper()
    }

    override suspend fun invoke(posts: List<Post>): CommonResult<Long> {
        val postEntities = posts.map { post ->
            mapper.modelToEntity(post)
        }

        postDao.insert(postEntities)?.let {
            return if (it.size == posts.size) {
                CommonResult.Success(it.size.toLong())
            } else {
                CommonResult.Success(0)
            }
        } ?: run {
            return CommonResult.Failure(DatabaseError("null"))
        }
    }
}