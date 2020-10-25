package com.technicaltest.app.extensions

import android.graphics.Bitmap
import androidx.palette.graphics.Palette

/**
 * Given a [Bitmap], get the dominant color of it.
 * @param defaultColor a color to return if call fails.
 * @return a color corresponding to the dominant bitmap color. Return the defaultColor
 * if the call fails.
 */
fun Bitmap.getDominantColor(defaultColor: Int): Int {
    var result = defaultColor
    Palette.Builder(this).generate {
        it ?: return@generate
        result = it.getDominantColor(defaultColor)
    }
    return result
}