package com.mbrains.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Commits {

    @SerializedName("commit")
    @Expose
    var commit: Commit? = null

    inner class Author {
        @SerializedName("name")
        @Expose
        var name: String? = null
        @SerializedName("email")
        @Expose
        var email: String? = null
        @SerializedName("date")
        @Expose
        var date: String? = null

    }

    inner class Commit {
        @SerializedName("author")
        @Expose
        var author: Author? = null
        @SerializedName("message")
        @Expose
        var message: String? = null

    }

}
