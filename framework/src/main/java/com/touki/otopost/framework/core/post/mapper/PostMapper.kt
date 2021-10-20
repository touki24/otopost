package com.touki.otopost.framework.core.post.mapper

import com.touki.otopost.common.constant.DATE_TIME_STANDARD_OLD
import com.touki.otopost.common.extension.orZero
import com.touki.otopost.common.extension.toDate
import com.touki.otopost.common.extension.toIso8601
import com.touki.otopost.common.mapper.EntityToModel
import com.touki.otopost.common.mapper.ModelToEntity
import com.touki.otopost.common.mapper.ResponseToModel
import com.touki.otopost.core.post.model.Post
import com.touki.otopost.framework.core.post.model.FetchPostResponse
import com.touki.otopost.framework.core.post.model.PostEntity

internal class PostMapper: ResponseToModel<FetchPostResponse, Post>, ModelToEntity<Post, PostEntity>, EntityToModel<PostEntity, Post> {

    override fun responseToModel(response: FetchPostResponse): Post {
        return Post(
            id = response.id.orZero(),
            title = response.title.orEmpty(),
            content = response.content.orEmpty(),
            publishedAt = response.publishedAt.toDate(DATE_TIME_STANDARD_OLD),
            createdAt = response.createdAt.toDate(DATE_TIME_STANDARD_OLD),
            updatedAt = response.updatedAt.toDate(DATE_TIME_STANDARD_OLD)
        )
    }

    override fun modelToEntity(model: Post): PostEntity {
        return PostEntity(
            id = model.id,
            title = model.title,
            content = model.content,
            publishedAt = model.publishedAt.toIso8601(),
            createdAt = model.createdAt.toIso8601(),
            updatedAt = model.updatedAt.toIso8601()
        )
    }

    override fun entityToModel(entity: PostEntity): Post {
        return Post(
            id = entity.id.orZero(),
            title = entity.title.orEmpty(),
            content = entity.content.orEmpty(),
            publishedAt = entity.publishedAt.toDate(DATE_TIME_STANDARD_OLD),
            createdAt = entity.createdAt.toDate(DATE_TIME_STANDARD_OLD),
            updatedAt = entity.updatedAt.toDate(DATE_TIME_STANDARD_OLD)
        )
    }
}