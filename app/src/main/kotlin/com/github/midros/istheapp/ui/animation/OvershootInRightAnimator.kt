package com.github.midros.istheapp.ui.animation

import androidx.recyclerview.widget.RecyclerView
import android.view.animation.OvershootInterpolator


/**
 * Created by luis rafael on 28/03/18.
 */
class OvershootInRightAnimator : BaseItemAnimator() {

    override fun animateRemoveImpl(holder: RecyclerView.ViewHolder) {
        holder.itemView.animate()
                .translationX(holder.itemView.rootView.width.toFloat())
                .setDuration(removeDuration)
                .setListener(DefaultRemoveAnimatorListener(holder))
                .setStartDelay(getRemoveDelay(holder))
                .start()
    }

    override fun preAnimateAddImpl(holder: RecyclerView.ViewHolder) {
        holder.itemView.translationX = holder.itemView.rootView.width.toFloat()
    }

    override fun animateAddImpl(holder: RecyclerView.ViewHolder) {
        holder.itemView.animate()
                .translationX(0f)
                .setDuration(addDuration)
                .setInterpolator(OvershootInterpolator(1f))
                .setListener(DefaultAddAnimatorListener(holder))
                .setStartDelay(getAddDelay(holder))
                .start()
    }

}
