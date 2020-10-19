package com.technicaltest.app.networking.api

import com.technicaltest.app.models.DataInfo
import retrofit2.Call
import retrofit2.http.GET

interface IPokemonAPI {
    @GET("api/v2/pokemon")
    fun pokemon(): Call<DataInfo>?
}