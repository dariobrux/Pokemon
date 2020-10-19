package com.technicaltest.app.networking

import androidx.lifecycle.MutableLiveData
import com.technicaltest.app.models.DataInfo
import com.technicaltest.app.networking.api.PokemonAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonRepository {

    private val pokemonAPI: PokemonAPI = RetrofitService.createService(PokemonAPI::class.java)

    fun getPokemon(): MutableLiveData<DataInfo> {
        val mutableLiveData: MutableLiveData<DataInfo> = MutableLiveData<DataInfo>()

        pokemonAPI.pokemon()?.enqueue(object : Callback<DataInfo> {
            override fun onResponse(call: Call<DataInfo>?, response: Response<DataInfo>) {
                if (response.isSuccessful) {
                    mutableLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<DataInfo>?, t: Throwable?) {
                mutableLiveData.value = null
            }
        })
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