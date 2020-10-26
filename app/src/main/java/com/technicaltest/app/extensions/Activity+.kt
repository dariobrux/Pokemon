package com.technicaltest.app.extensions

import android.app.Activity
import com.technicaltest.app.MainActivity

/**
 * Cast the activity to the [MainActivity].
 * @return the [MainActivity] class or null.
 */
fun Activity.toMainActivity() : MainActivity? {
    return this as? MainActivity
}