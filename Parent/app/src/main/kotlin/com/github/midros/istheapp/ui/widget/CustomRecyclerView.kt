package com.github.midros.istheapp.ui.widget

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.GridLayoutAnimationController
import com.github.midros.istheapp.R

/**
 * Created by luis rafael on 20/03/18.
 */
class CustomRecyclerView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (!isInEditMode) {
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.grid_layout_animation)
            startLayoutAnimation()
        }
    }

    override fun attachLayoutAnimationParameters(child: View, params: ViewGroup.LayoutParams, index: Int, count: Int) {
        if (adapter != null && layoutManager is GridLayoutManager) {

            var animParams: GridLayoutAnimationController.AnimationParameters? = params.layoutAnimationParameters as GridLayoutAnimationController.AnimationParameters?

            if (animParams == null) {
                animParams = GridLayoutAnimationController.AnimationParameters()
                params.layoutAnimationParameters = animParams
            }

            val columns = (layoutManager as GridLayoutManager).spanCount
            animParams.count = count
            animParams.index = index
            animParams.columnsCount = columns
            animParams.rowsCount = count / columns

            val invertedIndex = count - 1 - index
            animParams.column = columns - 1 - invertedIndex % columns
            animParams.row = animParams.rowsCount - 1 - invertedIndex / columns
        } else {
            super.attachLayoutAnimationParameters(child, params, index, count)
        }
    }


    private val dataObserver: AdapterDataObserver = object : AdapterDataObserver() {

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            setScrollPosition(positionStart)
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            setScrollPosition(positionStart)
        }
    }

    private fun setScrollPosition(positionStart: Int) {
        val lastVisiblePosition = (layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()
        if (lastVisiblePosition == -1 || positionStart >= adapter.itemCount - 1 && lastVisiblePosition == positionStart - 1) scrollToPosition(positionStart)
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
        if (adapter != null) {
            adapter.registerAdapterDataObserver(dataObserver)
            dataObserver.onChanged()
        }
    }
}