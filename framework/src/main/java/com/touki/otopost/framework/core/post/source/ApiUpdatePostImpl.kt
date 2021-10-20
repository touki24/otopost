package com.touki.otopost.framework.core.post.source

import com.touki.otopost.common.result.CommonResult
import com.touki.otopost.core.post.model.Post
import com.touki.otopost.core.post.source.ApiUpdatePost
import com.touki.otopost.framework.BuildConfig
import com.touki.otopost.framework.core.post.mapper.PostMapper
import com.touki.otopost.framework.core.post.model.FetchPostResponse
import com.touki.otopost.framework.core.post.model.UpdatePostRequest
import com.touki.otopost.framework.http.Deserializer
import com.touki.otopost.framework.http.HttpClient

internal class ApiUpdatePostImpl(private val httpClient: HttpClient): ApiUpdatePost {
    private val mapper by lazy {
        PostMapper()
    }

    private val deserializer by lazy {
        Deserializer<FetchPostResponse>(FetchPostResponse::class.java)
    }

    override suspend fun invoke(id: Int, title: String, content: String): CommonResult<Post> {
        val url = "${BuildConfig.BASE_URL}/posts/$id"
        val payload = UpdatePostRequest(title, content)
        val result = httpClient.put(url)
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