package com.touki.otopost.framework.core.post.model


import com.google.gson.annotations.SerializedName

internal class FetchPostsResponse : ArrayList<FetchPostsResponse.Item>(){
    data class Item(
        @SerializedName("id")
        val id: Int? = null,
        @SerializedName("title")
        val title: String? = null,
        @SerializedName("content")
        val content: String? = null,
        @SerializedName("published_at")
        val publishedAt: String? = null,
        @SerializedName("created_at")
        val createdAt: String? = null,
        @SerializedName("updated_at")
        val updatedAt: String? = null
    )
}