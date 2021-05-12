package com.dailiusprograming.posts_app.data.model

import com.google.gson.annotations.SerializedName

data class Company(
    @SerializedName("name")
    var companyName: String,
    @SerializedName("catchPhrase")
    var companyCatchPhrase: String,
    @SerializedName("bs")
    var companyBs: String
)