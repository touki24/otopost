package com.touki.otopost

import com.touki.otopost.core.post.repo.PostRepository
import com.touki.otopost.presentation.post.PostViewModel
import com.touki.otopost.presentation.post.create.PostCreateViewModel
import com.touki.otopost.presentation.post.detail.PostDetailViewModel
import com.touki.otopost.presentation.post.update.PostUpdateViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appPostModules = module {
    viewModel<PostUpdateViewModel> {
        PostUpdateViewModel(
            postRepository = get<PostRepository>()
        )
    }

    viewModel<PostCreateViewModel> {
        PostCreateViewModel(
            postRepository = get<PostRepository>()
        )
    }

    viewModel<PostViewModel> {
        PostViewModel(
            postRepository = get<PostRepository>()
        )
    }

    viewModel<PostDetailViewModel>() {
        PostDetailViewModel(
            postRepository = get<PostRepository>()
        )
    }
}