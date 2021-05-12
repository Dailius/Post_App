package com.dailiusprograming.posts_app.network

import com.dailiusprograming.posts_app.data.model.PostDetails
import com.dailiusprograming.posts_app.data.model.Posts
import com.dailiusprograming.posts_app.data.model.UserDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): Response<List<Posts>>

    @GET("posts/{post_id}")
    suspend fun getPostDetails(
        @Path("post_id") postId: Int,
    ): Response<PostDetails>

    @GET("users/{user_id}")
    suspend fun getUserDetails(
        @Path("user_id") userId: Int,
    ): Response<UserDetails>




}