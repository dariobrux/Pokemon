package com.dariobrux.pokemon.app.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel

/**
 *
 * Created by Dario Bruzzese on 22/10/2020.
 *
 */
class MainViewModel @ViewModelInject constructor(private val mainRepository: MainRepository) : ViewModel() {

    /**
     * @return the pokemon list.
     */
    fun getPokemonList() = mainRepository.getPokemonList()

//    /**
//     * Refresh the pokemon list, reloading from the first pokemon.
//     */
//    fun refreshPokemon(): LiveData<Resource<DataInfo>>? {
//        resetOffset()
//        return getPokemonList()
//    }

    fun resetOffset() {
        mainRepository.resetOffset()
    }
}