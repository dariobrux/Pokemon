package com.technicaltest.app.extensions

import android.graphics.Bitmap
import androidx.palette.graphics.Palette

/**
 * Given a [Bitmap], get the dominant color of it. It works async.
 * @param defaultColor a color to return if call fails.
 * @param onColorDetected function that returns the color when the task ends.
 * @return a color corresponding to the dominant bitmap color. Return the defaultColor
 * if the call fails.
 */
fun Bitmap.getDominantColor(defaultColor: Int, onColorDetected: ((Int) -> Unit)?) {
    var result = defaultColor
    Palette.Builder(this).generate {
        it ?: return@generate
        result = it.getDominantColor(defaultColor)
        onColorDetected?.invoke(result)
    }
}