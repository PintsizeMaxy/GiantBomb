package com.max.giantbomb.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface GiantBombClient {
    @GET("games/?api_key={apiKey}&filter=name:{game}&format=json")
    suspend fun getGamesList(
        @Path("apiKey") apiKey: String,
        @Path("game") game: String
    ): GamesList
}