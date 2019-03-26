package com.mbrains.data.models

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey

open class Repos(
    @PrimaryKey @SerializedName("id") var id: Int? = 0,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("forks_count") var forks_count: String? = null,
    @SerializedName("language") var language: String? = null,
    @SerializedName("html_url") var html_url: String? = null,
    @SerializedName("stargazers_count") var startCount: Int? = null,
    @Ignore
    @SerializedName("owner") var owner_: owner? = null,
    @SerializedName("full_name") var fullName: String? = null,
    @SerializedName("watchers_count") var watchersCount: Int? = null,
    var avatar_url: String? = null
): RealmObject(){
   inner class owner {
       @SerializedName("avatar_url") var avatarUrl: String? = null
   }
}