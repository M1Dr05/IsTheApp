package com.github.midros.child.ui.login

import com.github.midros.child.ui.base.InterfaceView

/**
 * Created by luis rafael on 9/03/18.
 */
interface InterfaceViewLogin : InterfaceView {

    fun successResult(boolean: Boolean)

    fun failedResult(throwable: Throwable)

}