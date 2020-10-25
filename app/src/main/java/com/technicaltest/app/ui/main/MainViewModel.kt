package com.technicaltest.app.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.technicaltest.app.models.DataInfo
import com.technicaltest.app.models.Pokemon
import com.technicaltest.app.other.Resource

/**
 *
 * Created by Dario Bruzzese on 22/10/2020.
 *
 */
class MainViewModel @ViewModelInject constructor(private val mainRepository: MainRepository) : ViewModel() {

    //    val isSortByName : MutableLiveData<Boolean> = MutableLiveData(false)
    val pokemonList = mutableListOf<Pokemon>()

    /**
     * @return the pokemon list.
     */
    fun getPokemon(): LiveData<Resource<DataInfo>>? {
        return mainRepository.getPokemon()
    }

    /**
     * Refresh the pokemon list, reloading from the first pokemon.
     */
    fun refreshPokemon(): LiveData<Resource<DataInfo>>? {
        mainRepository.resetOffset()
        return getPokemon()
    }

//    fun switchSort() {
//        isSortByName.value = (isSortByName.value)?.not()
//    }
}