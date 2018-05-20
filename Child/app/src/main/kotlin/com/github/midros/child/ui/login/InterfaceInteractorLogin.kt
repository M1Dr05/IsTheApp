package com.github.midros.child.ui.login

import com.github.midros.child.di.PerActivity
import com.github.midros.child.ui.base.InterfaceInteractor

/**
 * Created by luis rafael on 9/03/18.
 */
@PerActivity
interface InterfaceInteractorLogin<V : InterfaceViewLogin> : InterfaceInteractor<V> {
    fun signInDisposable(email: String, pass: String)
}