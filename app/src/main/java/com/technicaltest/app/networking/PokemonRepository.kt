package com.technicaltest.app.networking

import androidx.lifecycle.MutableLiveData
import com.technicaltest.app.models.DataInfo
import com.technicaltest.app.networking.api.PokemonAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonRepository {

    private val pokemonAPI: PokemonAPI = RetrofitService.createService(PokemonAPI::class.java)

    fun getPokemon(offset: Int, limit: Int): MutableLiveData<DataInfo> {
        val mutableLiveData: MutableLiveData<DataInfo> = MutableLiveData<DataInfo>()

        CoroutineScope(Dispatchers.IO).launch {
            val response = pokemonAPI.pokemon(offset, limit)

            withContext(Dispatchers.Main) {
                mutableLiveData.value = if (response?.isSuccessful == true) {
                    response.body()
                } else {
                    null
                }
            }
        }

        return mutableLiveData
    }

    companion object {
        private var pokemonRepository: PokemonRepository? = null
        val instance: PokemonRepository?
            get() {
                if (pokemonRepository == null) {
                    pokemonRepository = PokemonRepository()
                }
                return pokemonRepository
            }
    }

}