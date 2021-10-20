package com.touki.otopost.framework.core.post.source

import androidx.room.*
import com.touki.otopost.framework.core.post.model.PostEntity

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<PostEntity>): List<Long>?

    @Query("SELECT * FROM posts")
    fun selectAll(): List<PostEntity>?

    @Query("DELETE FROM posts")
    fun deleteAll(): Int?
}