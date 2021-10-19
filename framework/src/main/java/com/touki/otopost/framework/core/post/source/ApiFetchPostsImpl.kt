package com.touki.otopost.framework.core.post.source

import android.util.Log
import com.touki.otopost.common.result.CommonResult
import com.touki.otopost.common.result.ResultWithCache
import com.touki.otopost.core.post.model.Post
import com.touki.otopost.core.post.source.ApiFetchPosts
import com.touki.otopost.framework.BuildConfig
import com.touki.otopost.framework.core.post.extension.sortFromLatestToOldest
import com.touki.otopost.framework.core.post.mapper.PostMapper
import com.touki.otopost.framework.core.post.model.FetchPostsResponse
import com.touki.otopost.framework.http.Deserializer
import com.touki.otopost.framework.http.HttpClient
import java.util.*
import kotlin.Comparator

internal class ApiFetchPostsImpl(private val httpClient: HttpClient, private val postDao: PostDao): ApiFetchPosts {
    private val mapper by lazy {
        PostMapper()
    }

    private val deserializer by lazy {
        Deserializer<FetchPostsResponse>(FetchPostsResponse::class.java)
    }

    override suspend fun invoke(): ResultWithCache<List<Post>> {
        val url = "${BuildConfig.BASE_URL}/posts"
        val result = httpClient.get(url).log("API-POST").dispatch(timeout = 10000, timeoutRead = 10000).fold(
            success = {
                deserializer.deserialize(it)
            },
            failure = { error ->
                val postEntities = postDao.selectAll()
                val posts = postEntities?.map { postEntity ->
                    mapper.entityToModel(postEntity)
                }
                val sortedPosts = posts?.sortFromLatestToOldest() ?: run { listOf() }
                return ResultWithCache.Failure(error = error, cache = sortedPosts)
            }
        )

        val posts: MutableList<Post> = mutableListOf()
        result.forEach { item ->
            posts.add(mapper.responseToModel(item))
        }

        val sortedPosts = posts.sortFromLatestToOldest()

        val postEntities = sortedPosts.map { post ->
            mapper.modelToEntity(post)
        }

        postEntities.forEach {
            Log.d("TAG", "PostEntity($it) ")
        }

        postDao.deleteAll()
        postDao.insert(postEntities)

        return ResultWithCache.Success(sortedPosts)
    }
}