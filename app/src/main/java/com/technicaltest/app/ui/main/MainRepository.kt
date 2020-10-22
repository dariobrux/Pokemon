package com.technicaltest.app.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.technicaltest.app.database.PokemonDatabase
import com.technicaltest.app.models.DataInfo
import com.technicaltest.app.api.RetrofitService
import com.technicaltest.app.api.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class MainRepository {

    /**
     * Increment it to display the next set of items.
     */
    private var offset = 0

    /**
     * Max number of items to download in once.
     */
    private var limit = 20

    private val apiService: ApiService = RetrofitService.createService(ApiService::class.java)

    fun resetOffset() {
        offset = 0
    }

    fun getPokemon(context: Context): MutableLiveData<DataInfo> {
        val mutableLiveData: MutableLiveData<DataInfo> = MutableLiveData()

        CoroutineScope(Dispatchers.IO).launch {

            var dataInfo: DataInfo? = null

            // Read first the local pokemon list from database.
            val pokemonDatabase = PokemonDatabase.getInstance(context)
            val localPokemonList = pokemonDatabase.pokemonDao().getPokemonList(offset, limit)

            // If it is not empty, read and pass the data retrieved from database.
            if (!localPokemonList.isNullOrEmpty()) {
                Timber.d("Read the pokemon list from the database.")
                dataInfo = DataInfo().apply {
                    pokemonList = localPokemonList
                }
            } else {

                Timber.d("Trying to retrieve the pokemon list from url.")

                // If the database is empty, download the pokemon from the online API and
                // store them in the database.
                kotlin.runCatching {
                    apiService.pokemon(offset, limit)
                }.onSuccess { response ->
                    dataInfo = if (response?.isSuccessful == true) {
                        response.body()
                    } else {
                        null
                    }

                    // Store in the database.
                    dataInfo?.pokemonList?.let { pokemonList ->
                        Timber.d("Insert the pokemon list in the database.")
                        pokemonDatabase.pokemonDao().insertPokemonList(pokemonList)
                    }
                }.onFailure {
                    Timber.w("Problems while retrieve the pokemon list.")
                }
            }

            withContext(Dispatchers.Main) {
                mutableLiveData.value = dataInfo?.also {
                    offset += limit
                }
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