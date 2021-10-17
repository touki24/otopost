package com.touki.otopost.core.post.model

import java.util.Date

data class Post(
    val id: Int,
    val title: String,
    val content: String,
    val published_at: Date,
    val created_at: Date,
    val updated_at: Date) {

    override fun toString(): String {
        return "{" +
                "id:$id, " +
                "title:$title, " +
                "content:$content, " +
                "published_at:$published_at, " +
                "created_at:$created_at, " +
                "updated_at:$updated_at" +
                "}"
    }
}
