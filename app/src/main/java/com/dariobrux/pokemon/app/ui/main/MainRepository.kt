package com.dariobrux.pokemon.app.ui.main

import androidx.lifecycle.liveData
import com.dariobrux.pokemon.app.common.Resource
import com.dariobrux.pokemon.app.common.extension.toPokemonEntity
import com.dariobrux.pokemon.app.common.extension.toPokemonEntityList
import com.dariobrux.pokemon.app.data.local.PokemonDAO
import com.dariobrux.pokemon.app.data.local.model.PokemonEntity
import com.dariobrux.pokemon.app.data.remote.PokemonApiHelper
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
class MainRepository @Inject constructor(private val api: PokemonApiHelper, private val dao: PokemonDAO) {

    /**
     * Increment it to display the next set of items.
     */
    private var offset = 0

    /**
     * Max number of items to download in once.
     */
    private var limit = 20

    /**
     * Reset the offset to start from the first pokemon.
     */
    fun resetOffset() {
        offset = 0
    }

    fun getPokemonList() = liveData {

        Timber.tag(TAG).d("Retrieve Pokemon from offset $offset to ${offset + limit}")

        emit(Resource.loading(null))

        var pokemonEntityList: MutableList<PokemonEntity> = mutableListOf()

        kotlin.runCatching {
            dao.getPokemonList(offset, limit)
        }.onSuccess { pokemonList ->

            pokemonList?.let {
                pokemonEntityList.addAll(it)
            }
        }

        if (!pokemonEntityList.isNullOrEmpty()) {
            Timber.tag(TAG).d("Pokemon retrieved from Database.")
        } else {

            kotlin.runCatching {
                val resource = api.getPokemonList(offset, limit)
                val rootData = resource.data

                if (resource.status == Resource.Status.SUCCESS && rootData != null) {
                    pokemonEntityList = resource.data.results!!.toPokemonEntityList().toMutableList()

                    rootData.results?.forEachIndexed { index, value ->
                        value.url?.run {
                            api.getPokemonInfo(this)
                        }?.let {

                            it.data?.toPokemonEntity()?.let { pokemonEntity ->
                                pokemonEntityList[index] = pokemonEntity
                                Timber.tag(TAG).d("Storing ${pokemonEntity.name}...")

                                dao.insertPokemon(pokemonEntity)
                            }
                        }
                    }
                    Timber.tag(TAG).d("Pokemon retrieved from Database.")
                }
            }.onFailure {
                Timber.tag(TAG).d("Pokemon retrieved from Database.")
            }
        }

        offset += 20

        emit(Resource.success(pokemonEntityList))
    }

    companion object {
        private const val TAG = "PokemonDataSource"
    }
}