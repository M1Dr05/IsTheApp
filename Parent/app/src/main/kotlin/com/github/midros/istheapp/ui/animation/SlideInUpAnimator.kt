package com.github.midros.istheapp.ui.animation

import android.support.v7.widget.RecyclerView
import android.view.animation.OvershootInterpolator

/**
 * Created by luis rafael on 28/03/18.
 */
class SlideInUpAnimator : BaseItemAnimator() {

    override fun animateRemoveImpl(holder: RecyclerView.ViewHolder) {
        holder.itemView.animate()
                .translationY(holder.itemView.height.toFloat())
                .alpha(0f)
                .setDuration(removeDuration)
                .setInterpolator(mInterpolator)
                .setListener(DefaultRemoveAnimatorListener(holder))
                .setStartDelay(getRemoveDelay(holder))
                .start()
    }

    override fun preAnimateAddImpl(holder: RecyclerView.ViewHolder) {
        holder.itemView.translationY = holder.itemView.height.toFloat()
        holder.itemView.alpha = 0f
    }

    override fun animateAddImpl(holder: RecyclerView.ViewHolder) {
        holder.itemView.animate()
                .translationY(0f)
                .alpha(1f)
                .setDuration(addDuration)
                .setInterpolator(OvershootInterpolator(1f))
                .setListener(DefaultAddAnimatorListener(holder))
                .setStartDelay(getAddDelay(holder))
                .start()
    }
}
