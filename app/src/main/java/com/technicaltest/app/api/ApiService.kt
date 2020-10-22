package com.technicaltest.app.api

import com.technicaltest.app.models.DataInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/v2/pokemon")
    suspend fun pokemon(@Query("offset") offset: Int, @Query("limit") limit: Int): Response<DataInfo>

//    @GET
//    suspend fun pokemonData(@Url url: String) : Response<PokemonData>
}