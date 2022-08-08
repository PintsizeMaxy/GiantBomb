package com.max.giantbomb.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GiantBombClient {
    @GET("games/")
    suspend fun getGamesList(
        @Query("api_key") apiKey: String,
        @Query("filter") game: String,
        @Query("format") format: String = "json"
    ): GamesList
}