package com.touki.otopost

import com.touki.otopost.core.post.repo.PostRepository
import com.touki.otopost.presentation.post.PostViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appPostModules = module {
    viewModel<PostViewModel> {
        PostViewModel(
            postRepository = get<PostRepository>()
        )
    }
}