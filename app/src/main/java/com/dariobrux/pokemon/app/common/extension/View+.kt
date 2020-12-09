package com.dariobrux.pokemon.app.common.extension

import android.view.View

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