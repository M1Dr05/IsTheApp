package com.github.midros.child.ui.base

import android.content.Context
import com.github.midros.child.data.rxFirebase.InterfaceFirebase
import com.google.firebase.auth.FirebaseUser

/**
 * Created by luis rafael on 13/03/18.
 */
interface InterfaceInteractor<V : InterfaceView> {

    fun onAttach(view: V)

    fun onDetach()

    fun getView(): V

    fun getContext(): Context

    fun firebase(): InterfaceFirebase

    fun user(): FirebaseUser?

}