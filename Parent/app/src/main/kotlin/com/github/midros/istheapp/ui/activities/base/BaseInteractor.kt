package com.github.midros.istheapp.ui.activities.base

import android.content.Context
import android.support.v4.app.FragmentManager
import com.github.midros.istheapp.data.rxFirebase.InterfaceFirebase
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

/**
 * Created by luis rafael on 9/03/18.
 */
open class BaseInteractor<V : InterfaceView> @Inject constructor(private var supportFragment: FragmentManager, private var context: Context, private var firebase: InterfaceFirebase) : InterfaceInteractor<V> {

    private var view: V? = null

    override fun onAttach(view: V) {
        this.view = view
    }

    override fun onDetach() {
        view = null
    }

    override fun getView(): V? = view

    override fun getContext(): Context = context

    override fun getSupportFragmentManager(): FragmentManager = supportFragment

    override fun firebase(): InterfaceFirebase = firebase

    override fun user(): FirebaseUser? = firebase.getUser()

}