package com.technicaltest.app.api

import com.technicaltest.app.models.DataInfo
import com.technicaltest.app.models.PokemonData
import dagger.Provides
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : BaseDataSource() {

//    override suspend fun pokemon(offset: Int, limit: Int): Response<DataInfo>? = apiService.pokemon(offset, limit)
//
//    override suspend fun pokemonData(url: String): Response<PokemonData>? = apiService.pokemonData(url)

    suspend fun getPokemon(offset: Int, limit: Int) = getResult { apiService.pokemon(offset, limit) }
//    suspend fun getCharacter(id: Int) = getResult { characterService.getCharacter(id) }
}