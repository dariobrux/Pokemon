package com.technicaltest.app.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technicaltest.app.models.DataInfo
import com.technicaltest.app.models.Pokemon
import com.technicaltest.app.other.Resource
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(val mainRepository: MainRepository) : ViewModel() {

//    val pokemonList: LiveData<Resource<DataInfo>> = mainRepository.getPokemon()

    private var pokemonLiveData: LiveData<Resource<DataInfo>>? = null
//    private val _pokemonLiveData = MutableLiveData<Resource<DataInfo>>()

//    val pokemonLiveData: LiveData<Resource<DataInfo>>
//        get() = _pokemonLiveData

//    fun init(context: Context) {
//        if (mainRepository != null) {
//            return
//        }
//        this.context = context
//        mainRepository = MainRepository.instance
//    }

    fun getPokemon(): LiveData<Resource<DataInfo>>? {
        pokemonLiveData = mainRepository.getPokemon()
        return pokemonLiveData
    }

//    init {
//        getPokemon()
//    }
//
//    private fun getPokemon() = viewModelScope.launch {
//        _pokemonLiveData.postValue(Resource.loading(null))
//        mainRepository.getPokemon()?.let {
//            if (it.isSuccessful) {
//                _pokemonLiveData.postValue(Resource.success(it.body()))
//            } else {
//                _pokemonLiveData.postValue(Resource.error(it.errorBody().toString(), null))
//            }
//        }
//    }


    fun refreshPokemon(): LiveData<Resource<DataInfo>>? {
        mainRepository.resetOffset()
        return getPokemon()
    }
}