package com.touki.otopost.framework.core.post.source

import android.util.Log
import com.touki.otopost.common.result.CommonResult
import com.touki.otopost.core.post.model.Post
import com.touki.otopost.core.post.source.ApiFetchPosts
import com.touki.otopost.framework.BuildConfig
import com.touki.otopost.framework.core.post.mapper.PostMapper
import com.touki.otopost.framework.core.post.model.FetchPostsResponse
import com.touki.otopost.framework.http.Deserializer
import com.touki.otopost.framework.http.HttpClient

internal class ApiFetchPostsImpl(private val httpClient: HttpClient): ApiFetchPosts {
    private val mapper by lazy {
        PostMapper()
    }

    private val deserializer by lazy {
        Deserializer<FetchPostsResponse>(FetchPostsResponse::class.java)
    }

    override suspend fun invoke(): CommonResult<List<Post>> {
        val url = "${BuildConfig.BASE_URL}/posts"
        val result = httpClient.get(url).log("API-POST").dispatch(timeout = 25000, timeoutRead = 25000).fold(
            success = {
                Log.d("API-POST-IMPL", "response: $it")
                deserializer.deserialize(it)
            },
            failure = { error ->
                return CommonResult.Failure(error)
            }
        )

        val posts = result.map { item ->
            mapper.responseToModel(item)
        }
        return CommonResult.Success(posts)
    }
}