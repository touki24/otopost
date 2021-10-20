package com.touki.otopost.framework.core.post.source

import com.touki.otopost.common.result.CommonResult
import com.touki.otopost.core.post.model.Post
import com.touki.otopost.core.post.source.ApiCreatePost
import com.touki.otopost.framework.BuildConfig
import com.touki.otopost.framework.core.post.mapper.PostMapper
import com.touki.otopost.framework.core.post.model.CreatePostRequest
import com.touki.otopost.framework.core.post.model.FetchPostResponse
import com.touki.otopost.framework.http.Deserializer
import com.touki.otopost.framework.http.HttpClient

internal class ApiCreatePostImpl(private val httpClient: HttpClient): ApiCreatePost {
    private val mapper by lazy {
        PostMapper()
    }

    private val deserializer by lazy {
        Deserializer<FetchPostResponse>(FetchPostResponse::class.java)
    }

    override suspend fun invoke(title: String, content: String): CommonResult<Post> {
        val url = "${BuildConfig.BASE_URL}/posts"
        val payload = CreatePostRequest(title, content)
        val result = httpClient.post(url)
            .body(payload)
            .log("API-POST")
            .dispatch(timeout = 10000, timeoutRead = 10000)
            .fold(
                success = {
                    deserializer.deserialize(it)
                },
                failure = { error ->
                    return CommonResult.Failure(error)
                })
        return CommonResult.Success(mapper.responseToModel(result))
    }
}