package com.dariobrux.pokemon.app.common.extensions

import android.content.res.Resources

/**
 * Convert a dp value to corresponding px.
 * @return the converted value in px.
 */
fun Int.dpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}