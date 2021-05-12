package com.dailiusprograming.posts_app.di

import android.content.Context
import androidx.room.Room
import com.dailiusprograming.posts_app.data.local.PostsDao
import com.dailiusprograming.posts_app.data.local.PostsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): PostsDatabase =
        Room.databaseBuilder(appContext, PostsDatabase::class.java, "posts.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideMovieDao(appDatabase: PostsDatabase): PostsDao {
        return appDatabase.postsDao()
    }

}


