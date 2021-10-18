package com.touki.otopost.framework

import com.touki.otopost.core.post.repo.PostRepository
import com.touki.otopost.core.post.source.ApiFetchPost
import com.touki.otopost.core.post.source.ApiFetchPosts
import com.touki.otopost.framework.core.post.repo.PostRepositoryImpl
import com.touki.otopost.framework.core.post.source.ApiFetchPostImpl
import com.touki.otopost.framework.core.post.source.ApiFetchPostsImpl
import com.touki.otopost.framework.http.FuelClient
import com.touki.otopost.framework.http.HttpClient
import org.koin.dsl.module

val frameworkCorePostModules = module {
    factory<ApiFetchPosts> {
        ApiFetchPostsImpl(
            httpClient = get<HttpClient>()
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
            apiFetchPost = get<ApiFetchPost>()
        )
    }

    factory<HttpClient> {
        FuelClient()
    }
}