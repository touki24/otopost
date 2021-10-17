package com.touki.otopost.framework

import com.touki.otopost.core.post.repo.PostRepository
import com.touki.otopost.core.post.source.ApiFetchPosts
import com.touki.otopost.framework.core.post.repo.PostRepositoryImpl
import com.touki.otopost.framework.core.post.source.ApiFetchPostsImpl
import org.koin.dsl.module

val frameworkCorePostModules = module {
    factory<ApiFetchPosts> {
        ApiFetchPostsImpl()
    }

    factory<PostRepository> {
        PostRepositoryImpl(
            apiFetchPosts = get<ApiFetchPosts>()
        )
    }
}