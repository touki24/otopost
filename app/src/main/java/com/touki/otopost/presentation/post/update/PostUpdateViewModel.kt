package com.touki.otopost.presentation.post.update

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.touki.otopost.core.post.model.Post
import com.touki.otopost.core.post.repo.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostUpdateViewModel(private val postRepository: PostRepository): ViewModel() {
    private var _updatedPost: MutableLiveData<Post> = MutableLiveData()
    val updatedPost: LiveData<Post> = _updatedPost

    private var _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    fun updatePost(id: Int, title: String, content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            postRepository.updatePost(id, title, content).fold(
                success = { posts ->
                    _updatedPost.postValue(posts)
                },
                failure = { error ->
                    _error.postValue(error.message)
                }
            )
        }
    }
}