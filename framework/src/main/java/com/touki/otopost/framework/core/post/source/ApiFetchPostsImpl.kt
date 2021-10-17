package com.touki.otopost.framework.core.post.source

import com.touki.otopost.common.result.CommonResult
import com.touki.otopost.core.post.model.Post
import com.touki.otopost.core.post.source.ApiFetchPosts
import com.touki.otopost.framework.core.post.mapper.PostMapper
import com.touki.otopost.framework.core.post.model.FetchPostsResponse

internal class ApiFetchPostsImpl: ApiFetchPosts {
    private val mapper by lazy {
        PostMapper()
    }

    override suspend fun invoke(): CommonResult<List<Post>> {
        val posts = dummyFetchPostsResponse.map { item ->
            mapper.responseToModel(item)
        }
        return CommonResult.Success(posts)
    }
}

internal val dummyFetchPostsResponse = FetchPostsResponse().apply {
    add(FetchPostsResponse.Item(
        id = 1,
        title = "Hello world",
        content = "Hello world dang danga",
        publishedAt = "2021-10-13T05:07:57.208Z",
        createdAt = "2021-10-13T05:07:52.434Z",
        updatedAt = "2021-10-15T02:25:07.912Z"
    ))
    add(FetchPostsResponse.Item(
        id = 2,
        title = "Hello world",
        content = "Hello world dang danga",
        publishedAt = "2021-10-13T05:07:57.208Z",
        createdAt = "2021-10-13T05:07:52.434Z",
        updatedAt = "2021-10-15T02:25:07.912Z"
    ))
    add(FetchPostsResponse.Item(
        id = 3,
        title = "Hello world",
        content = "Hello world dang danga",
        publishedAt = "2021-10-15T11:11:49.139Z",
        createdAt = "2021-10-15T11:11:49.140Z",
        updatedAt = "2021-10-15T11:11:49.139Z"
    ))
}