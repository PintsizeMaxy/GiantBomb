package com.max.giantbomb.remote

import com.google.gson.annotations.SerializedName

data class GamesList(
    @SerializedName("results")
    val results: List<GameData>
)

data class GameData(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val title: String,
    @SerializedName("image")
    val imageUrl: String,
    @SerializedName("description")
    val description: String
)
