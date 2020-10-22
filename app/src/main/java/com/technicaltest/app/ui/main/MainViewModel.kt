package com.technicaltest.app.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.technicaltest.app.models.DataInfo

class MainViewModel : ViewModel() {

    private var context: Context? = null
    private var mainRepository: MainRepository? = null
    private var pokemonLiveData: MutableLiveData<DataInfo>? = null

    fun init(context: Context) {
        if (mainRepository != null) {
            return
        }
        this.context = context
        mainRepository = MainRepository.instance
    }

    fun getPokemon(): LiveData<DataInfo>? {
        context?.let {
            pokemonLiveData = mainRepository?.getPokemon(it)
        }
        return pokemonLiveData
    }
}