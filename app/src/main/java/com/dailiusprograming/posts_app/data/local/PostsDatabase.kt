package com.dailiusprograming.posts_app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dailiusprograming.posts_app.data.model.PostDetails
import com.dailiusprograming.posts_app.data.model.Posts
import com.dailiusprograming.posts_app.data.model.UserDetails

@Database(
        entities = [
                Posts::class,
                PostDetails::class,
                UserDetails::class
        ],
        version = 3
)
abstract class PostsDatabase : RoomDatabase() {
    abstract fun postsDao(): PostsDao
}