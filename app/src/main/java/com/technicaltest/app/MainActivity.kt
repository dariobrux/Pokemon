package com.technicaltest.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.lifecycle.lifecycleScope
import com.technicaltest.app.extensions.readValue
import com.technicaltest.app.extensions.storeValue
import com.technicaltest.app.preferences.PreferenceKeys
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.coroutines.launch
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
        readNightMode()

        // Add a button on the toolbar
        toolbar?.inflateMenu(R.menu.menu)
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_menu -> {
                    // Switch theme
                    switchTheme()
                }
            }
            false
        }
    }

    /**
     * Switch theme. If current theme is night, switch to day.
     * If current theme is day, switch to night,
     */
    private fun switchTheme() {
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

    /**
     * Read the night mode from the DataStore.
     * Restore the night theme if in the DataStore is stored night.
     * Restore the day theme if in the DataStore is stored day.
     */
    private fun readNightMode() {
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
    }
}