package com.github.midros.child.services.base

import android.content.Context
import com.github.midros.child.data.rxFirebase.InterfaceFirebase
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

/**
 * Created by luis rafael on 22/03/18.
 */
open class BaseInteractorService<S : InterfaceService> @Inject constructor(private var context: Context, private var firebase: InterfaceFirebase) : InterfaceInteractorService<S> {

    private var service: S? = null

    override fun onAttach(service: S) {
        this.service = service
    }

    override fun onDetach() {
        this.service = null
    }

    override fun getService(): S? = service

    override fun getContext(): Context = context

    override fun firebase(): InterfaceFirebase = firebase

    override fun user(): FirebaseUser? = firebase.getUser()

}