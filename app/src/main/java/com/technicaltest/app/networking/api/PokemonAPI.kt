package com.technicaltest.app.networking.api

import com.technicaltest.app.models.DataInfo
import com.technicaltest.app.models.PokemonData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonAPI {

    @GET("api/v2/pokemon")
    suspend fun pokemon(@Query("offset") offset: Int, @Query("limit") limit: Int): Response<DataInfo>?

    @GET
    suspend fun pokemonData(@Url url: String) : Response<PokemonData>?
}