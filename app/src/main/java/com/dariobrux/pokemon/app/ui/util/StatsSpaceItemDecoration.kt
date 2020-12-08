package com.dariobrux.pokemon.app.ui.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 *
 * Created by Dario Bruzzese on 28/11/2020.
 *
 * This class is the ItemDecoration useful for the RecyclerView for Pokemon stats.
 */
class StatsSpaceItemDecoration(private val space: Int) : ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        parent.adapter?.let {
            outRect.bottom = space
        }
    }
}