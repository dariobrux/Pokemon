package com.dariobrux.pokemon.app.data.remote

import com.dariobrux.pokemon.app.data.remote.model.info.PokemonInfo
import retrofit2.Response
import retrofit2.http.Url
import javax.inject.Inject

/**
 *
 * Created by Dario Bruzzese on 22/10/2020.
 *
 * This class get the results from the api service and map the result object
 * to an useful object with the status.
 *
 */

class PokemonApiHelper @Inject constructor(private val service: PokemonService) : ApiHelper() {

    /**
     * Get the pokemon list.
     * @param offset it will be retrieved a list starting from this value
     * @param limit maximum number of items to retrieve.
     * @return the list of pokemon mapped into a [Response] object.
     */
    suspend fun getPokemonList(offset: Int, limit: Int) = getResult {
        service.getPokemonList(offset, limit)
    }

    /**
     * Get the [PokemonInfo] containing info of a single Pokemon.
     * @param url the url to call to get the info.
     * @return the [PokemonInfo] object mapped into an async response.
     */
    suspend fun getPokemonInfo(@Url url: String) = getResult {
        service.getPokemonInfo(url)
    }

}