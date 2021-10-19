package com.touki.otopost.framework.core.post.model


import com.google.gson.annotations.SerializedName

internal data class UpdatePostRequest(
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String
)