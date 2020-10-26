package com.technicaltest.app

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_activity.*
import timber.log.Timber
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

    /**
     * Current items visualization. This is a public field
     * because it must be visible from the MainFragment.
     */
    @Inject
    lateinit var visualization: MutableLiveData<Visualization>

    /**
     * Current theme
     */
    private var theme = Theme.UNDEFINED

    enum class Theme {
        NIGHT_MODE_NO,
        NIGHT_MODE_YES,
        UNDEFINED;

        /**
         * Invert theme.
         * Night Mode -> Day Mode
         * Day Mode | Undefined -> Night Mode
         */
        fun inverse(): Theme {
            return when (this) {
                NIGHT_MODE_YES -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    NIGHT_MODE_NO
                }
                else -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    NIGHT_MODE_YES
                }
            }
        }
    }

    enum class Visualization {
        LIST,
        GRID
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Check what is the current theme
        when ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_NO -> {
                Timber.tag(TAG).d("Night mode is not active, we're in day time")
                theme = Theme.NIGHT_MODE_NO
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                Timber.tag(TAG).d("Night mode is active, we're at night")
                theme = Theme.NIGHT_MODE_YES
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                Timber.tag(TAG).d("We don't know what mode we're in, assume notnight")
                theme = Theme.UNDEFINED
            }
        }

        // Add a button on the toolbar
        toolbar?.let {
            it.inflateMenu(R.menu.menu)
            it.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_theme -> {
                        // Switch theme
                        theme.inverse()
                    }
                }
                false
            }
        }

        // Add the bottom bar item listener to change the items visualization.
        bottomBar?.onItemSelectedListener = { view, menuItem ->
            when (menuItem.itemId) {
                R.id.list -> {
                    visualization.value = Visualization.LIST
                }
                R.id.grid -> {
                    visualization.value = Visualization.GRID
                }
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"

    }
}