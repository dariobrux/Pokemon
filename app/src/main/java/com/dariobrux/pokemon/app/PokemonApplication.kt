package com.dariobrux.pokemon.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 *
 * Created by Dario Bruzzese on 22/10/2020.
 *
 * This is the application class declared in Manifest.
 */

@HiltAndroidApp
class PokemonApplication : Application() {

    override fun onCreate() {
        super.onCreate()


//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

//    /**
//     * Read the night mode from the DataStore.
//     * Restore the night theme if in the DataStore is stored night.
//     * Restore the day theme if in the DataStore is stored day.
//     */
//    private fun readNightMode(defaultFunc: () -> Unit) {
//        supervisorScope {
//            dataStore.readValue(PreferenceKeys.THEME_NIGHT, {
//                performThemeChanged()
//                isNightMode = if (this) {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                    true
//                } else {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                    false
//                }
//            }, {
//                defaultFunc.invoke()
//            })
//        }
//    }
}
