package com.technicaltest.app.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.technicaltest.app.models.DataInfo
import com.technicaltest.app.other.Resource

/**
 *
 * Created by Dario Bruzzese on 22/10/2020.
 *
 */
class MainViewModel @ViewModelInject constructor(private val mainRepository: MainRepository) : ViewModel() {

    private var pokemonLiveData: LiveData<Resource<DataInfo>>? = null

    /**
     * @return the pokemon list.
     */
    fun getPokemon(): LiveData<Resource<DataInfo>>? {
        pokemonLiveData = mainRepository.getPokemon()
        return pokemonLiveData
    }

    /**
     * Refresh the pokemon list, reloading from the first pokemon.
     */
    fun refreshPokemon(): LiveData<Resource<DataInfo>>? {
        mainRepository.resetOffset()
        return getPokemon()
    }
}