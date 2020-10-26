package com.technicaltest.app.di

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.MutableLiveData
import com.technicaltest.app.BuildConfig
import com.technicaltest.app.MainActivity
import com.technicaltest.app.api.PokemonApiHelper
import com.technicaltest.app.api.PokemonDataApiHelper
import com.technicaltest.app.api.PokemonDataService
import com.technicaltest.app.api.PokemonService
import com.technicaltest.app.database.PokemonDao
import com.technicaltest.app.database.PokemonDatabase
import com.technicaltest.app.other.Constants
import com.technicaltest.app.ui.info.InfoRepository
import com.technicaltest.app.ui.main.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 *
 * Created by Dario Bruzzese on 22/10/2020.
 *
 * This singleton object is a bucket from where we will get all the dependencies from.
 *
 */
@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLocalStorage(@ApplicationContext context: Context) = context.createDataStore(name = "sample")

    @Singleton
    @Provides
    fun provideVisualization() = MutableLiveData(MainActivity.Visualization.LIST)

    @Singleton
    @Provides
    fun provideSorting() = MutableLiveData(MainActivity.Sorting.NUM)

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun providePokemonService(retrofit: Retrofit): PokemonService = retrofit.create(PokemonService::class.java)

    @Provides
    @Singleton
    fun providePokemonDataService(retrofit: Retrofit): PokemonDataService = retrofit.create(PokemonDataService::class.java)

    @Provides
    @Singleton
    fun providePokemonApiHelper(pokemonService: PokemonService) = PokemonApiHelper(pokemonService)

    @Provides
    @Singleton
    fun providePokemonDataApiHelper(pokemonDataService: PokemonDataService) = PokemonDataApiHelper(pokemonDataService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = PokemonDatabase.getInstance(appContext)

    @Singleton
    @Provides
    fun providePokemonDao(db: PokemonDatabase) = db.pokemonDao()

    @Singleton
    @Provides
    fun provideMainRepository(pokemonApiHelper: PokemonApiHelper, dao: PokemonDao) = MainRepository(pokemonApiHelper, dao)

    @Singleton
    @Provides
    fun provideInfoRepository(pokemonDataApiHelper: PokemonDataApiHelper, dao: PokemonDao) = InfoRepository(pokemonDataApiHelper, dao)
}
