package com.touki.otopost.framework.core.post.extension

import com.touki.otopost.core.post.model.Post

fun List<Post>.sortFromLatestToOldest(): List<Post> {
    val postsToSort: MutableList<Post> = mutableListOf()
    this.forEach { item ->
        postsToSort.add(item)
    }
    postsToSort.sortWith { o1, o2 -> o2.updatedAt.compareTo(o1.updatedAt) } // sort from latest to oldest
    return postsToSort
}