package com.mbrains.data.models

import com.google.gson.annotations.SerializedName

data class ReposResponse (
    @SerializedName("items") val items: List<Repos>
)