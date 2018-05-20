package com.github.midros.child.services.base

import android.content.Context
import com.github.midros.child.data.rxFirebase.InterfaceFirebase
import com.github.midros.child.di.PerService
import com.google.firebase.auth.FirebaseUser

/**
 * Created by luis rafael on 22/03/18.
 */
interface InterfaceInteractorService<S : InterfaceService> {

    fun onAttach(service: S)

    fun onDetach()

    fun getService(): S?

    fun getContext(): Context

    fun firebase(): InterfaceFirebase

    fun user(): FirebaseUser?

}