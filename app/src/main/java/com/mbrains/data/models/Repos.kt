package com.mbrains.data.models

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

public open class Repos(
    @PrimaryKey @SerializedName("id") var id: Int? = 0,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("forks_count") var forks_count: String? = null,
    @SerializedName("language") var language: String? = null,
    @SerializedName("html_url") var url: String? = null,
    @SerializedName("stargazers_count") var startCount: Int? = null,
    @SerializedName("watchers_count") var watchersCount: Int? = null
) : RealmObject()