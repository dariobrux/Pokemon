package com.technicaltest.app.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.technicaltest.app.database.PokemonDatabase
import com.technicaltest.app.models.DataInfo
import com.technicaltest.app.models.PokemonData
import com.technicaltest.app.networking.RetrofitService
import com.technicaltest.app.networking.api.PokemonAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainRepository {

    private val pokemonAPI: PokemonAPI = RetrofitService.createService(PokemonAPI::class.java)

    fun getPokemon(context: Context, offset: Int, limit: Int): MutableLiveData<DataInfo> {
        val mutableLiveData: MutableLiveData<DataInfo> = MutableLiveData()

        CoroutineScope(Dispatchers.IO).launch {

            val dataInfo : DataInfo?

            // Read first the local pokemon list from database.
            val pokemonDatabase = PokemonDatabase.getInstance(context)
            val localPokemonList = pokemonDatabase.pokemonDao().getPokemonList(offset, limit)

            // If it is not empty, read and pass the data retrieved from database.
            if (!localPokemonList.isNullOrEmpty()) {
                dataInfo = DataInfo().apply {
                    pokemonList = localPokemonList
                }
            } else {

                // If the database is empty, download the pokemon from the online API and
                // store them in the database.
                val response = pokemonAPI.pokemon(offset, limit)
                dataInfo = if (response?.isSuccessful == true) {
                    response.body()
                } else {
                    null
                }

                // Store in the database.
                dataInfo?.pokemonList?.let { pokemonList ->
                    pokemonDatabase.pokemonDao().insertPokemonList(pokemonList)
                }
            }

            withContext(Dispatchers.Main) {
                mutableLiveData.value = dataInfo
            }
        }

        return mutableLiveData
    }

//    fun getPokemonData(url:String) : MutableLiveData<PokemonData> {
//        val mutableLiveData: MutableLiveData<PokemonData> = MutableLiveData()
//
//        CoroutineScope(Dispatchers.IO).launch {
//
//            // Read first the local pokemon list from database.
//            // If it is empty, download the pokemon from the online API and
//            // store them in the database.
//            // If it is not empty, read and pass the data
//            val pokemonDatabase = PokemonDatabase.getInstance(context)
//            val localPokemonList = pokemonDatabase.pokemonDao().getPokemonList()
//
//            if (!localPokemonList.isNullOrEmpty()) {
//                mutableLiveData.value = localPokemonList
//            }
//
//            val response = pokemonAPI.pokemonData(url)
//
//            withContext(Dispatchers.Main) {
//                mutableLiveData.value = if (response?.isSuccessful == true) {
//                    response.body()
//                } else {
//                    null
//                }
//            }
//        }
//
//        return mutableLiveData
//    }

    companion object {
        private var mainRepository: MainRepository? = null
        val instance: MainRepository?
            get() {
                if (mainRepository == null) {
                    mainRepository = MainRepository()
                }
                return mainRepository
            }
    }

}