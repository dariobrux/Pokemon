package com.technicaltest.app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.technicaltest.app.models.DataInfo
import com.technicaltest.app.models.PokemonData
import com.technicaltest.app.networking.PokemonRepository

class MainViewModel : ViewModel() {

    private var pokemonRepository: PokemonRepository? = null

    private var pokemonLiveData: MutableLiveData<DataInfo>? = null

    /**
     * Increment it to display the next set of items.
     */
    private var offset = 0

    /**
     * Max number of items to download in once.
     */
    private var limit = 300

    fun init() {
        if (pokemonRepository != null) {
            return
        }
        pokemonRepository = PokemonRepository.instance
    }

    fun getPokemon(): LiveData<DataInfo>? {
        pokemonLiveData = pokemonRepository?.getPokemon(offset, limit)
        offset += limit
        return pokemonLiveData
    }
}