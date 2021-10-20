package com.touki.otopost.framework.core.post.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "title")
    val title: String? = null,
    @ColumnInfo(name = "content")
    val content: String? = null,
    @ColumnInfo(name = "published_at")
    val publishedAt: String? = null,
    @ColumnInfo(name = "created_at")
    val createdAt: String? = null,
    @ColumnInfo(name = "updated_at")
    val updatedAt: String? = null,
) {
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