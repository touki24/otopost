package com.touki.otopost.presentation.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.touki.otopost.core.post.model.Post
import com.touki.otopost.core.post.repo.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostViewModel(private val postRepository: PostRepository): ViewModel() {

    private val _posts: MutableLiveData<List<Post>> = MutableLiveData()
    val posts: LiveData<List<Post>> = _posts

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    fun fetchPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            postRepository.fetchPosts().fold(
                success = { posts ->
                    _posts.postValue(posts)
                },
                failure = { error ->
                    _error.postValue(error.message)
                }
            )
        }
    }
}