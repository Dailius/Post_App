package com.dailiusprograming.posts_app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "posts_details")
data class PostDetails (
    @PrimaryKey (autoGenerate = false)
    @SerializedName("id")
    val postsId: Int,
    var userId: Int,
    var title: String,
    var body: String
)