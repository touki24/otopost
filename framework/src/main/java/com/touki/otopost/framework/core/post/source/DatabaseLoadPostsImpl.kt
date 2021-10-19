package com.touki.otopost.framework.core.post.source

import android.util.Log
import com.touki.otopost.common.result.CommonResult
import com.touki.otopost.common.result.DatabaseError
import com.touki.otopost.common.result.ResultWithCache
import com.touki.otopost.core.post.model.Post
import com.touki.otopost.core.post.source.ApiFetchPosts
import com.touki.otopost.core.post.source.DatabaseLoadPosts
import com.touki.otopost.framework.BuildConfig
import com.touki.otopost.framework.core.post.extension.sortFromLatestToOldest
import com.touki.otopost.framework.core.post.mapper.PostMapper
import com.touki.otopost.framework.core.post.model.FetchPostsResponse
import com.touki.otopost.framework.http.Deserializer
import com.touki.otopost.framework.http.HttpClient
import java.util.*
import kotlin.Comparator

internal class DatabaseLoadPostsImpl(private val postDao: PostDao): DatabaseLoadPosts {
    private val mapper by lazy {
        PostMapper()
    }

    override suspend fun invoke(): CommonResult<List<Post>> {
        val postEntities = postDao.selectAll()
        val posts = postEntities?.map { postEntity ->
            mapper.entityToModel(postEntity)
        }

        val sortedPosts = posts?.sortFromLatestToOldest() ?: run { listOf() }

        return CommonResult.Success(sortedPosts)
    }
}