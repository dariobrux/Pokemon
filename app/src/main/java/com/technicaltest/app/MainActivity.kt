package com.technicaltest.app

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import androidx.lifecycle.lifecycleScope
import com.technicaltest.app.extensions.getFromLocalStorage
import com.technicaltest.app.extensions.readValue
import com.technicaltest.app.extensions.storeValue
import com.technicaltest.app.preferences.PreferenceKeys
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Locale.filter
import javax.inject.Inject


/**
 *
 * Created by Dario Bruzzese on 22/10/2020.
 *
 * This is the main activity, where the application starts its
 * navigation.
 *
 * It is annotated by AndroidEntryPoint to integrate Hilt in this
 * activity.
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var dataStore: DataStore<Preferences>

    private var isNightMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Read theme value from the DataStore
        lifecycleScope.launch {
            dataStore.readValue(PreferenceKeys.THEME_NIGHT) {
                isNightMode = if (this) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    false
                }
            }
        }

        // Add a button on the toolbar
        toolbar?.inflateMenu(R.menu.menu)
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_menu -> {

                    // Read theme value from the DataStore
                    isNightMode = if (isNightMode) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        false
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        true
                    }

                    lifecycleScope.launch {
                        dataStore.storeValue(PreferenceKeys.THEME_NIGHT, isNightMode)
                    }
                }
            }
            false
        }
    }
//
//    /**
//     * Save the value
//     * @param isNightTheme true if theme is night, false if is day.
//     */
//    private suspend fun updateTheme(isNightTheme: Boolean) {
//        dataStore.addToLocalStorage {
//            this[PreferenceKeys.THEME_NIGHT] = isNightTheme
//        }
//    }
//
//    /**
//     * Combination of higher-order function and
//     * extension function to change the way to save data using DataStore.
//     * @param mutableFunc function to invoke after edit.
//     */
//    private suspend fun DataStore<Preferences>.addToLocalStorage(mutableFunc: MutablePreferences.() -> Unit) {
//        edit {
//            mutableFunc(it)
//        }
//    }
//
//    private fun emitStoredValue(): Flow<Boolean> {
//        return dataStore.data.catch {
//            if (it is IOException) {
//                emit(emptyPreferences())
//            } else {
//                throw it
//            }
//        }.map {
//            it[PreferenceKeys.THEME_NIGHT] ?: false
//        }
//    }
//


}