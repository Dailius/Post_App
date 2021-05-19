package com.dailiusprograming.posts_app.data.local

import androidx.room.*
import com.dailiusprograming.posts_app.data.model.PostDetails
import com.dailiusprograming.posts_app.data.model.Posts
import com.dailiusprograming.posts_app.data.model.UserDetails
import com.dailiusprograming.posts_app.data.model.relations.PostAndUser

@Dao
interface PostsDao {

    @Query("SELECT * FROM posts")
    suspend fun getAllPosts(): List<Posts>

    @Query("SELECT * FROM posts_details")
    suspend fun getPostDetails(): PostDetails

    @Query("SELECT * FROM user WHERE userId = :userId")
    suspend fun getUserDetails(userId: Int): UserDetails

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<Posts>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPostDetails(postDetails: PostDetails?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserDetails(userDetails: UserDetails?)

    @Query("DELETE FROM posts")
    suspend fun deleteAllPosts()

    @Query("DELETE FROM posts_details")
    suspend fun deleteAllPostDetails()

    @Query("DELETE FROM user")
    suspend fun deleteAllUserDetails()

    @Transaction
    @Query("SELECT * FROM posts_details WHERE postsId = :postsId")
    suspend fun getPostAndUser(postsId: Int): PostAndUser

}