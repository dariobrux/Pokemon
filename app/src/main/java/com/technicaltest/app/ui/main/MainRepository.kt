package com.technicaltest.app.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.technicaltest.app.api.ApiHelperImpl
import com.technicaltest.app.database.PokemonDao
import com.technicaltest.app.models.DataInfo
import com.technicaltest.app.other.Resource
import com.technicaltest.app.other.performGetOperation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelperImpl, private val pokemonDao: PokemonDao) {

    /**
     * Increment it to display the next set of items.
     */
    private var offset = 0

    /**
     * Max number of items to download in once.
     */
    private var limit = 20

//    private val apiService: ApiService = RetrofitService.createService(ApiService::class.java)

    fun resetOffset() {
        offset = 0
    }

//    fun getPokemon() = performGetOperation(
//        databaseQuery = { pokemonDao.getPokemonList(offset, limit) },
//        networkCall = { apiHelper.pokemon(offset, limit) },
//        saveCallResult = { pokemonDao.insertPokemonList(it) }
//    )

//    suspend fun getPokemon() = apiHelper.pokemon(offset, limit)

    fun getPokemon(): LiveData<Resource<DataInfo>> {
        val mutableLiveData: MutableLiveData<Resource<DataInfo>> = MutableLiveData()

        CoroutineScope(Dispatchers.IO).launch {

            var dataInfo: DataInfo? = null

            // Read first the local pokemon list from database.
            val localPokemonList = pokemonDao.getPokemonList(offset, limit)

            // If it is not empty, read and pass the data retrieved from database.
            if (!localPokemonList.value.isNullOrEmpty()) {
                Timber.d("Read the pokemon list from the database.")
                dataInfo = DataInfo().apply {
                    pokemonList = localPokemonList.value
                }
            } else {

                Timber.d("Trying to retrieve the pokemon list from url.")

                // If the database is empty, download the pokemon from the online API and
                // store them in the database.
                kotlin.runCatching {
                    apiHelper.getPokemon(offset, limit)
                }.onSuccess {
                    dataInfo = if (it.status == Resource.Status.SUCCESS) {
                        it.data
                    } else {
                        null
                    }

                    // Store in the database.
                    dataInfo?.pokemonList?.let { pokemonList ->
                        Timber.d("Insert the pokemon list in the database.")
                        pokemonDao.insertPokemonList(pokemonList)
                    }
                }.onFailure {
                    Timber.w("Problems while retrieve the pokemon list.")
                }
            }

            withContext(Dispatchers.Main) {
                mutableLiveData.value = Resource(Resource.Status.SUCCESS, dataInfo, null)
                dataInfo?.also {
                    offset += limit
                }
            }
        }

        return mutableLiveData
    }
//
//    companion object {
//        private var mainRepository: MainRepository? = null
//        val instance: MainRepository?
//            get() {
//                if (mainRepository == null) {
//                    mainRepository = MainRepository()
//                }
//                return mainRepository
//            }
//    }
}