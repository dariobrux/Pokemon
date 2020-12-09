package com.dariobrux.pokemon.app.data.remote

import com.dariobrux.pokemon.app.data.remote.model.info.PokemonInfo
import com.dariobrux.pokemon.app.data.remote.model.root.RootData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 *
 * Created by Dario Bruzzese on 22/10/2020.
 *
 * Interface for Retrofit that contains the declaration of the API to invoke.
 *
 */

interface PokemonService {


    /**
     * Get the [RootData] with the data to download.
     * @param offset it will be retrieved a list starting from this value
     * @param limit maximum number of items to retrieve. Default 20.
     * @return the [RootData] mapped into an async response.
     */
    @GET("api/v2/pokemon")
    suspend fun getPokemonList(@Query("offset") offset: Int, @Query("limit") limit: Int = 20): Response<RootData>

    /**
     * Get the [PokemonInfo] containing info of a single Pokemon.
     * @param url the url to call to get the info.
     * @return the [PokemonInfo] object mapped into an async response.
     */
    @GET
    suspend fun getPokemonInfo(@Url url: String): Response<PokemonInfo>
}