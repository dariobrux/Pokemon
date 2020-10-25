package com.technicaltest.app.extensions

import android.graphics.Color
import junit.framework.TestCase

class ColorTest : TestCase() {

    fun testChangeAlpha() {
        val color = Color.rgb(80, 80, 80)
        val newColor = color.changeAlpha(80)
        assertEquals(Color.argb(80, 80, 80, 80), newColor)
    }
}