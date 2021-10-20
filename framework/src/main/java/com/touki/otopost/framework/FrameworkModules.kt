package com.touki.otopost.framework

import android.content.Context
import com.touki.otopost.core.post.repo.PostRepository
import com.touki.otopost.core.post.source.*
import com.touki.otopost.framework.core.post.repo.PostRepositoryImpl
import com.touki.otopost.framework.core.post.source.*
import com.touki.otopost.framework.core.post.source.ApiCreatePostImpl
import com.touki.otopost.framework.core.post.source.ApiDeletePostImpl
import com.touki.otopost.framework.core.post.source.ApiFetchPostImpl
import com.touki.otopost.framework.core.post.source.ApiFetchPostsImpl
import com.touki.otopost.framework.core.post.source.ApiUpdatePostImpl
import com.touki.otopost.framework.database.OtopostDatabase
import com.touki.otopost.framework.http.FuelClient
import com.touki.otopost.framework.http.HttpClient
import org.koin.dsl.module

val frameworkCorePostModules = module {
    factory<DatabaseLoadPosts> {
        DatabaseLoadPostsImpl(
            postDao = get<PostDao>()
        )
    }

    factory<ApiUpdatePost> {
        ApiUpdatePostImpl(
            httpClient = get<HttpClient>()
        )
    }

    factory<ApiCreatePost> {
        ApiCreatePostImpl(
            httpClient = get<HttpClient>()
        )
    }

    factory<ApiDeletePost> {
        ApiDeletePostImpl(
            httpClient = get<HttpClient>()
        )
    }

    factory<ApiFetchPosts> {
        ApiFetchPostsImpl(
            httpClient = get<HttpClient>(),
            postDao = get<PostDao>()
        )
    }

    factory<ApiFetchPost> {
        ApiFetchPostImpl(
            httpClient = get<HttpClient>()
        )
    }

    factory<PostRepository> {
        PostRepositoryImpl(
            apiFetchPosts = get<ApiFetchPosts>(),
            apiFetchPost = get<ApiFetchPost>(),
            apiDeletePost = get<ApiDeletePost>(),
            apiCreatePost = get<ApiCreatePost>(),
            apiUpdatePost = get<ApiUpdatePost>(),
            databaseLoadPosts = get<DatabaseLoadPosts>()
        )
    }
}

val frameworkDatabaseModules = module {
    factory<OtopostDatabase?> {
        OtopostDatabase.getInstance(get<Context>())
    }

    factory<PostDao> {
        get<OtopostDatabase>().getPostDao()
    }
}

val frameworkHttpModules = module {
    factory<HttpClient> {
        FuelClient()
    }
}