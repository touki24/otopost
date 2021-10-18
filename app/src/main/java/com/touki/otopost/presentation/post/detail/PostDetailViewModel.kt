package com.touki.otopost.presentation.post.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.touki.otopost.core.post.model.Post
import com.touki.otopost.core.post.repo.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostDetailViewModel(private val postRepository: PostRepository): ViewModel() {
    private val _post: MutableLiveData<Post> = MutableLiveData()
    val post: LiveData<Post> = _post

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    fun fetchPost(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            postRepository.fetchPost(postId).fold(
                success = { posts ->
                    _post.postValue(posts)
                },
                failure = { error ->
                    _error.postValue(error.message)
                }
            )
        }
    }
}