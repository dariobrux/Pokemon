package com.dariobrux.pokemon.app.common.manager

import android.graphics.Color
import androidx.annotation.ColorInt

/**
 *
 * Created by Dario Bruzzese on 22/10/2020.
 *
 */
object ColorManager {

    /**
     * Given a color int, change its alpha between
     * 0 and 255.
     * @param alpha the new value
     * @return a new color int
     */
    fun changeAlpha(@ColorInt color: Int, alpha: Int): Int {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return Color.argb(alpha, red, green, blue)
    }
}