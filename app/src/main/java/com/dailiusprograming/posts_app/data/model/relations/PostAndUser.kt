package com.dailiusprograming.posts_app.data.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.dailiusprograming.posts_app.data.model.PostDetails
import com.dailiusprograming.posts_app.data.model.UserDetails


data class PostAndUser(
    @Embedded val postsDetails: PostDetails,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val user: UserDetails
)
