package com.github.midros.istheapp.ui.widget

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import com.github.midros.istheapp.utils.ConstFun.dpToPx

/**
 * Created by luis rafael on 20/03/18.
 */
class ItemOffsetDecoration(
        private val spacing: Int = 4f.dpToPx().toInt()
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        outRect.set(spacing, spacing, spacing, spacing)
    }
}