package com.technicaltest.app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.technicaltest.app.models.DataInfo
import com.technicaltest.app.networking.PokemonRepository


class MainViewModel : ViewModel() {

    private var mutableLiveData: MutableLiveData<DataInfo>? = null
    private var pokemonRepository: PokemonRepository? = null

    fun init() {
        if (mutableLiveData != null) {
            return
        }
        pokemonRepository = PokemonRepository.instance
        mutableLiveData = pokemonRepository?.getPokemon()
    }

    fun getPokemonRepository(): LiveData<DataInfo>? {
        return mutableLiveData
    }
}