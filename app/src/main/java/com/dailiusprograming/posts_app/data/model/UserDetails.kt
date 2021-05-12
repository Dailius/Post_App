package com.dailiusprograming.posts_app.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "user")
data class UserDetails(
    @PrimaryKey (autoGenerate = false)
    @SerializedName("id")
    val userId: Int,
    @SerializedName("name")
    var personFullName: String,
    var username: String,
    var email: String,
    @Embedded
    var address: Address,
    @ColumnInfo(name = "phone")
    var phone: String,
    var website: String,
    @Embedded
    var company: Company
)

