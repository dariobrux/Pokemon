package com.technicaltest.app.ui.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class VerticalSpaceItemDecoration(private val space: Int) : ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        parent.adapter?.let {
            val position = parent.getChildAdapterPosition(view)
            if (position != it.itemCount - 1) {
                outRect.bottom = space;
            }
            if (position % 2 == 0) {
                outRect.right = space
            }
        }
    }
}