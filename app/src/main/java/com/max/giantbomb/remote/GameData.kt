package com.max.giantbomb.remote

import com.google.gson.annotations.SerializedName

data class GamesList(
    @SerializedName("results")
    val results: List<GameData>
)

data class GameData(
    @SerializedName("guid")
    val id: String?,
    @SerializedName("name")
    val title: String?,
    @SerializedName("image")
    val image: Image?,
    @SerializedName("description")
    val description: String?
)

data class Image(
    @SerializedName("original_url")
    val imageUrl: String?
)
