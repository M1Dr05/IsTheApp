package com.github.midros.istheapp.ui.activities.base

import cn.pedant.SweetAlert.SweetAlertDialog
import com.github.midros.istheapp.di.component.ActivityComponent
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.Disposable

/**
 * Created by luis rafael on 8/03/18.
 */
interface InterfaceView {

    fun getComponent(): ActivityComponent?

    fun getPermissions(): RxPermissions?

    fun subscribePermission(permission: Permission, msgRationale: String, msgDenied: String, granted: () -> Unit)

    fun showDialog(alertType: Int, title: Int, msg: String?, txtPositiveButton: Int?, cancellable: Boolean = false, action: SweetAlertDialog.() -> Unit)

    fun hideDialog()

    fun showError(message: String)

    fun showMessage(message: String)

    fun addDisposable(disposable: Disposable)

    fun clearDisposable()

    fun successResult(boolean: Boolean)

    fun failedResult(throwable: Throwable)


}