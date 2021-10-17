package com.touki.otopost.framework.core.post.mapper

import com.touki.otopost.common.constant.DATE_TIME_STANDARD
import com.touki.otopost.common.extension.orZero
import com.touki.otopost.common.extension.toDate
import com.touki.otopost.common.mapper.ResponseToModel
import com.touki.otopost.core.post.model.Post
import com.touki.otopost.framework.core.post.model.FetchPostsResponse

class PostMapper: ResponseToModel<FetchPostsResponse.Item, Post> {

    override fun responseToModel(response: FetchPostsResponse.Item): Post {
        return Post(
            id = response.id.orZero(),
            title = response.title.orEmpty(),
            content = response.content.orEmpty(),
            published_at = response.publishedAt.toDate(DATE_TIME_STANDARD),
            created_at = response.createdAt.toDate(DATE_TIME_STANDARD),
            updated_at = response.updatedAt.toDate(DATE_TIME_STANDARD)
        )
    }
}