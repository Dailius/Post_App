package com.dailiusprograming.posts_app.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailiusprograming.posts_app.data.model.PostDetails
import com.dailiusprograming.posts_app.data.model.Posts
import com.dailiusprograming.posts_app.data.model.relations.PostAndUser
import com.dailiusprograming.posts_app.repository.Repository
import com.dailiusprograming.posts_app.util.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PostsListViewModel
@ViewModelInject
constructor(
    private val repository: Repository
) : ViewModel() {

    private var _postsList = MutableLiveData<DataResult<List<Posts>>>()
    val postsList = _postsList

    private var _postDetail = MutableLiveData<DataResult<PostDetails>>()
    val postDetail = _postDetail

    private var _postAndUser = MutableLiveData<DataResult<PostAndUser>>()
    val postAndUser = _postAndUser


    init {
        fetchPosts(DataResult.Status.INIT)
    }

    fun fetchPosts(status: DataResult.Status) {
        viewModelScope.launch {

            repository.fetchPosts(status).collect {
                _postsList.value = it
            }
        }
    }

    fun fetchPost(postId: Int, userId: Int) {
        viewModelScope.launch(Dispatchers.Main) {

            repository.fetchPostDetail(postId = postId, userId = userId).collect {
                _postAndUser.value = it
            }
        }
    }
}