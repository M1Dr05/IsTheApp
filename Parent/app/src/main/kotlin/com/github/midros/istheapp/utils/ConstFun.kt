package com.github.midros.istheapp.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.provider.Settings
import android.support.annotation.IdRes
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.github.clans.fab.FloatingActionMenu
import com.github.midros.istheapp.R
import com.google.firebase.database.Query
import com.pawegio.kandroid.runOnUiThread
import com.pawegio.kandroid.start
import com.pawegio.kandroid.startActivity

/**
 * Created by luis rafael on 9/03/18.
 */
object ConstFun {


    inline fun <reified V : View> View.find(@IdRes id: Int): V = findViewById(id)

    inline fun <reified T : Any> Activity.startAndFinishActivity() {
        startActivity<T>()
        finish()
        animateActivity()
    }

    fun Activity.animateActivity() {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fun Context.openAppSystemSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        intent.start(this)
    }

    fun runThread(sleep: Long, action: () -> Unit): Thread = Thread(Runnable {
        while (true) {
            try {
                Thread.sleep(sleep)
                runOnUiThread { action() }
            } catch (e: InterruptedException) {
                break
            }
        }
    })

    fun Context.alertDialog(alertType: Int, title: Int, msg: String?, txtPositiveButton: Int?, cancellable: Boolean = false, action: SweetAlertDialog.() -> Unit): SweetAlertDialog {
        val dialog = SweetAlertDialog(this, alertType).apply {
            titleText = getString(title)
            setCancelable(cancellable)

            if (alertType != SweetAlertDialog.PROGRESS_TYPE) {
                contentText = msg
                confirmText = getString(txtPositiveButton!!)
                if (cancellable) cancelText = getString(android.R.string.cancel)
                showCancelButton(cancellable)
            } else progressHelper.barColor = ContextCompat.getColor(this@alertDialog, R.color.colorPrimary)
        }
        action(dialog)
        return dialog
    }

    //fun Context.setAnimations(resourceId: Int): Animation = AnimationUtils.loadAnimation(this, resourceId)

    fun ImageView.setImageUrl(context: Context, url: String) {
        Glide.with(context).load(url)
                .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                .apply(RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        //.error(context.getDrawable(R.drawable.ic_placeholder_error))
                        .placeholder(context.getDrawable(R.color.colorGrayTransp))
                )
                .into(this)
    }

    fun convertCurrentDuration(currentDuration: Int): String {
        val seconds = (currentDuration % 60000 / 1000).toString()
        val minutes = (currentDuration / 60000).toString()
        return if (seconds.length == 1) "$minutes:0$seconds" else "$minutes:$seconds"
    }

    //FirebaseRecyclerOptions
    inline fun <reified T> firebaseOptions(query: Query): FirebaseRecyclerOptions<T> =
            FirebaseRecyclerOptions.Builder<T>().setQuery(query, T::class.java).build()

    fun Float.dpToPx() = this * Resources.getSystem().displayMetrics.density

    fun customAnimationMenu(menu: FloatingActionMenu, drawableOpen: Int, drawableClose: Int) {
        val set = AnimatorSet()
        val scaleOutX = ObjectAnimator.ofFloat(menu.menuIconView, "scaleX", 1.0f, 0.2f)
        val scaleOutY = ObjectAnimator.ofFloat(menu.menuIconView, "scaleY", 1.0f, 0.2f)
        val scaleInX = ObjectAnimator.ofFloat(menu.menuIconView, "scaleX", 0.2f, 1.0f)
        val scaleInY = ObjectAnimator.ofFloat(menu.menuIconView, "scaleY", 0.2f, 1.0f)
        scaleOutX.duration = 50
        scaleOutY.duration = 50
        scaleInX.duration = 150
        scaleInY.duration = 150
        scaleInX.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                menu.menuIconView.setImageResource(if (menu.isOpened) drawableOpen else drawableClose)
            }
        })
        set.play(scaleOutX).with(scaleOutY)
        set.play(scaleInX).with(scaleInY).after(scaleOutX)
        set.interpolator = OvershootInterpolator(2f)
        menu.iconToggleAnimatorSet = set
    }

}