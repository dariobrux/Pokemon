package com.technicaltest.app.api

import com.technicaltest.app.models.DataInfo
import com.technicaltest.app.models.PokemonData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiHelper {

    suspend fun pokemon(offset: Int, limit: Int): Response<DataInfo>?

    suspend fun pokemonData(url: String): Response<PokemonData>?
}