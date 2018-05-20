package com.github.midros.istheapp.ui.activities.main

import android.content.Context
import android.support.v4.app.FragmentManager
import com.github.midros.istheapp.data.rxFirebase.InterfaceFirebase
import com.github.midros.istheapp.ui.activities.base.BaseInteractor
import com.github.midros.istheapp.utils.MyCountDownTimer
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import com.github.midros.istheapp.data.preference.DataSharePreference.getTimeFinishApp
import javax.inject.Inject

/**
 * Created by luis rafael on 9/03/18.
 */
class InteractorMain<V : InterfaceViewMain> @Inject constructor(supportFragment: FragmentManager, context: Context, firebase: InterfaceFirebase) : BaseInteractor<V>(supportFragment, context, firebase), InterfaceInteractorMain<V> {

    private var startTime = (1 * 60 * getContext().getTimeFinishApp()).toLong()
    private var interval = (1 * 1000).toLong()
    private var myCountDownTimer: MyCountDownTimer? = null

    override fun setCountDownTimer() {
        myCountDownTimer = MyCountDownTimer(startTime, interval, { getView()!!.onFinishCount() })
        myCountDownTimer!!.start()
    }

    override fun restartCountDownTimer() {
        if (myCountDownTimer != null) {
            myCountDownTimer!!.cancel()
            myCountDownTimer!!.start()
        }
    }

    override fun cancelCountDownTimer() {
        if (myCountDownTimer != null) {
            myCountDownTimer!!.cancel()
        }
    }

    override fun getDatabaseReference(child: String): DatabaseReference = firebase().getDatabaseReference(child)

    override fun getStorageReference(child: String): StorageReference = firebase().getStorageReference(child)

    override fun signOut() {
        cancelCountDownTimer()
        getView()!!.clearDisposable()
        firebase().signOut()
        if (firebase().getUser() == null) getView()!!.signOutView()
    }

}