package com.touki.otopost.presentation.post.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.touki.otopost.core.post.model.Post
import com.touki.otopost.core.post.repo.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostCreateViewModel(private val postRepository: PostRepository): ViewModel() {
    private var _createdPost: MutableLiveData<Post> = MutableLiveData()
    val createdPost: LiveData<Post> = _createdPost

    private var _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    fun createPost(title: String, content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            postRepository.createPost(title, content).fold(
                success = { posts ->
                    _createdPost.postValue(posts)
                },
                failure = { error ->
                    _error.postValue(error.message)
                }
            )
        }
    }
}