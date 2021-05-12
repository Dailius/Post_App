package com.dailiusprograming.posts_app.data.remote

import com.dailiusprograming.posts_app.data.model.PostDetails
import com.dailiusprograming.posts_app.data.model.Posts
import com.dailiusprograming.posts_app.data.model.UserDetails
import com.dailiusprograming.posts_app.network.ApiService
import com.dailiusprograming.posts_app.util.DataResult
import com.dailiusprograming.posts_app.util.ErrorUtils
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class RemoteData @Inject constructor(
    private val retrofit: Retrofit
) {

    suspend fun fetchPosts(): DataResult<List<Posts>> {
        val apiService = retrofit.create(ApiService::class.java)
        val result = apiService.getPosts()
        return getResponse(
            request = { result },
            defaultErrorMessage = "Error fetching remote data source"
        )
    }

    suspend fun fetchPostDetails(postId: Int): DataResult<PostDetails> {
        val apiService = retrofit.create(ApiService::class.java)
        val result = apiService.getPostDetails(postId)
        return getResponse(
            request = { result },
            defaultErrorMessage = "Error fetching remote data source"
        )
    }

    suspend fun fetchUserDetails(userId: Int): DataResult<UserDetails> {
        val apiService = retrofit.create(ApiService::class.java)
        val result = apiService.getUserDetails(userId)
        return getResponse(
            request = { result },
            defaultErrorMessage = "Error fetching remote data source"
        )
    }

    private suspend fun <T> getResponse(
        request: suspend () -> Response<T>,
        defaultErrorMessage: String
    ): DataResult<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                return DataResult.success(result.body())
            } else {
                val errorResponse = ErrorUtils.parseError(result, retrofit)
                DataResult.error(
                    errorResponse?.status_message ?: defaultErrorMessage,
                    errorResponse
                )
            }
        } catch (e: Throwable) {
            DataResult.error("Unknown Error", null)
        }
    }
}