package com.github.midros.istheapp.ui.activities.base

import android.content.Context
import android.support.v4.app.FragmentManager
import com.github.midros.istheapp.data.rxFirebase.InterfaceFirebase
import com.google.firebase.auth.FirebaseUser

/**
 * Created by luis rafael on 9/03/18.
 */
interface InterfaceInteractor<V : InterfaceView> {

    fun onAttach(view: V)

    fun onDetach()

    fun getView(): V?

    fun getContext(): Context

    fun getSupportFragmentManager(): FragmentManager

    fun firebase(): InterfaceFirebase

    fun user(): FirebaseUser?

}