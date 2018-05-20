package com.github.midros.istheapp.ui.activities.register

import com.github.midros.istheapp.di.PerActivity
import com.github.midros.istheapp.ui.activities.base.InterfaceInteractor

/**
 * Created by luis rafael on 10/03/18.
 */
@PerActivity
interface InterfaceInteractorRegister<V : InterfaceViewRegister> : InterfaceInteractor<V> {

    fun signUpDisposable(email: String, pass: String)

}