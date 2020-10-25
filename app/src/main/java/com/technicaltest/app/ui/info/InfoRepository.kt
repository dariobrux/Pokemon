package com.technicaltest.app.ui.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.technicaltest.app.api.PokemonApiHelper
import com.technicaltest.app.api.PokemonDataApiHelper
import com.technicaltest.app.database.PokemonDao
import com.technicaltest.app.models.DataInfo
import com.technicaltest.app.models.PokemonData
import com.technicaltest.app.other.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

/**
 *
 * Created by Dario Bruzzese on 22/10/2020.
 *
 * This class is the repository that handles the communication
 * between the restful api and the database.
 *
 */
class InfoRepository @Inject constructor(private val pokemonDataApiHelper: PokemonDataApiHelper, private val pokemonDao: PokemonDao) {

    /**
     * Get the list of the pokemon from a restful api or from the database.
     * Read first the local pokemon list from db. If this list is not empty,
     * notify that some pokemon are available.
     * If the local list is empty, download the pokemon from the api. Then,
     * store this list in the database.
     * @return the [DataInfo] object mapped into a [Resource], inside a [LiveData].
     */
    fun getPokemonData(name: String, url: String): LiveData<Resource<PokemonData>> {
        val mutableLiveData: MutableLiveData<Resource<PokemonData>> = MutableLiveData()

        CoroutineScope(Dispatchers.IO).launch {

            var pokemonData: PokemonData? = null

            // Read first the local pokemon list from database.
            val localPokemon = pokemonDao.getPokemonData(name)

            // If it is not empty, read and pass the data retrieved from database.
            if (localPokemon != null) {
                Timber.d("Read the pokemon from the database.")
                pokemonData = localPokemon
            } else {

                Timber.d("Trying to retrieve the pokemon from url.")

                // If the database is empty, download the pokemon from the api and
                // store it in the database.
                kotlin.runCatching {
                    pokemonDataApiHelper.getPokemonData(url)
                }.onSuccess {
                    pokemonData = if (it.status == Resource.Status.SUCCESS) {
                        it.data
                    } else {
                        null
                    }

                    // Store in the database.
                    pokemonData?.let { data ->
                        Timber.d("Insert the pokemon in the database.")
                        pokemonDao.insertPokemonData(data)
                    }
                }.onFailure {
                    Timber.w("Problems while retrieve the pokemon list.")
                }
            }

            withContext(Dispatchers.Main) {
                mutableLiveData.value = Resource(Resource.Status.SUCCESS, pokemonData, null)
            }
        }

        return mutableLiveData
    }
}