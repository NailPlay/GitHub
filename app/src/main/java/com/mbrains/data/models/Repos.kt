package com.mbrains.data.models

import com.google.gson.annotations.SerializedName

data class Repos(
    @SerializedName("id") var id : Int?,
    @SerializedName("name") var name : String?,
    @SerializedName("description") var description : String?,
    @SerializedName("forks_count") var forks_count : String?,
    @SerializedName("language") var language : String?,
    @SerializedName("html_url") var url : String?,
    @SerializedName("stargazers_count") var startCount : Int?,
    @SerializedName("watchers_count") var watchersCount : Int?
)