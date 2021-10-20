package com.touki.otopost.core.post.model

import java.util.Date

data class Post(
    val id: Int,
    val title: String,
    val content: String,
    val publishedAt: Date,
    val createdAt: Date,
    val updatedAt: Date) {

    override fun toString(): String {
        return "{" +
                "id:$id, " +
                "title:$title, " +
                "content:$content, " +
                "published_at:$publishedAt, " +
                "created_at:$createdAt, " +
                "updated_at:$updatedAt" +
                "}"
    }
}
