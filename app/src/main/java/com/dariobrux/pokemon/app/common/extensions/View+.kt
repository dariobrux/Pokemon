package com.dariobrux.pokemon.app.common.extensions

import android.animation.ValueAnimator
import android.view.View
import androidx.cardview.widget.CardView

/**
 * Set the visibility to VISIBLE.
 */
fun View.toVisible() {
    this.visibility = View.VISIBLE
}

/**
 * Set the visibility to GONE.
 */
fun View.toGone() {
    this.visibility = View.GONE
}