package com.technicaltest.app.networking.api

import com.technicaltest.app.models.DataInfo
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface PokemonAPI {
    @GET("api/v2/pokemon")
    suspend fun pokemon(): Response<DataInfo>?
}