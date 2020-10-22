package com.technicaltest.app.api

import com.technicaltest.app.models.DataInfo
import com.technicaltest.app.models.PokemonData
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun pokemon(offset: Int, limit: Int): Response<DataInfo>? = apiService.pokemon(offset, limit)

    override suspend fun pokemonData(url: String): Response<PokemonData>? = apiService.pokemonData(url)
}