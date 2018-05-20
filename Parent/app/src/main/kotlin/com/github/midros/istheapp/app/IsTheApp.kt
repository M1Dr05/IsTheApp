package com.github.midros.istheapp.app

import android.app.Application
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ProcessLifecycleOwner
import android.content.Intent
import android.os.Process
import com.github.midros.istheapp.data.preference.DataSharePreference.getLockPin
import com.github.midros.istheapp.data.preference.DataSharePreference.getLockState
import com.github.midros.istheapp.ui.activities.lock.LockActivity
import com.github.midros.istheapp.utils.Consts.TAG
import com.pawegio.kandroid.IntentFor
import com.pawegio.kandroid.d
import com.pawegio.kandroid.start

/**
 * Created by luis rafael on 28/03/18.
 */
class IsTheApp : Application(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        d(TAG, "App in background")
        Process.killProcess(Process.myPid())
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        d(TAG, "App in foreground")
        checkLockScreen()
    }

    private fun checkLockScreen() {
        if (getLockState() && getLockPin() != "") {
            val intent = IntentFor<LockActivity>(this)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.start(this)
        }
    }

}