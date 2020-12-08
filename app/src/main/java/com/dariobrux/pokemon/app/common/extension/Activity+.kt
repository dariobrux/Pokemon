package com.dariobrux.pokemon.app.common.extension

import android.app.Activity
import com.dariobrux.pokemon.app.ui.MainActivity

/**
 * Cast the activity to the [MainActivity].
 * @return the [MainActivity] class or null.
 */
fun Activity.toMainActivity() : MainActivity? {
    return this as? MainActivity
}