package com.github.midros.child.ui.base

import android.content.DialogInterface
import com.github.midros.child.di.component.ActivityComponent
import com.tbruyelle.rxpermissions2.Permission
import io.reactivex.disposables.Disposable

/**
 * Created by luis rafael on 13/03/18.
 */
interface InterfaceView {

    fun getComponent(): ActivityComponent?

    fun subscribePermission(permission: Permission, msgRationale: Int, msgDenied: Int, granted: () -> Unit)

    fun showAlertDialog(message: Int, txtPositiveButton: Int, cancelable: Boolean = true, func: DialogInterface.() -> Unit)

    fun showLoading(msg: String)

    fun hideLoading()

    fun showError(message: String)

    fun showMessage(message: String)

    fun addDisposable(disposable: Disposable)

    fun clearDisposable()

}