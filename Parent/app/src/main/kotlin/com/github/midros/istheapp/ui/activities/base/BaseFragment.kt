package com.github.midros.istheapp.ui.activities.base

import android.os.Bundle
import android.support.v4.app.Fragment
import cn.pedant.SweetAlert.SweetAlertDialog
import com.github.midros.istheapp.di.component.ActivityComponent
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.Disposable

/**
 * Created by luis rafael on 9/03/18.
 */
abstract class BaseFragment : Fragment(), InterfaceView {

    companion object {
        @JvmStatic
        var baseActivity: BaseActivity? = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        baseActivity = (activity as BaseActivity)
    }

    override fun getComponent(): ActivityComponent? =
            if (baseActivity != null) baseActivity!!.getComponent() else null

    override fun getPermissions(): RxPermissions? = if (baseActivity != null) baseActivity!!.getPermissions() else null

    override fun subscribePermission(permission: Permission, msgRationale: String, msgDenied: String, granted: () -> Unit) {
        if (baseActivity != null) baseActivity!!.subscribePermission(permission, msgRationale, msgDenied, granted)
    }

    override fun showMessage(message: String) {
        if (baseActivity != null)
            baseActivity!!.showMessage(message)
    }


    override fun showDialog(alertType: Int, title: Int, msg: String?, txtPositiveButton: Int?, cancellable: Boolean, action: SweetAlertDialog.() -> Unit) {
        if (baseActivity != null)
            baseActivity!!.showDialog(alertType, title, msg, txtPositiveButton, cancellable, action)
    }

    override fun hideDialog() {
        if (baseActivity != null)
            baseActivity!!.hideDialog()
    }


    override fun showError(message: String) {
        if (baseActivity != null)
            baseActivity!!.showError(message)
    }


    override fun addDisposable(disposable: Disposable) {
        if (baseActivity != null)
            baseActivity!!.addDisposable(disposable)
    }

    override fun clearDisposable() {
        if (baseActivity != null)
            baseActivity!!.clearDisposable()
    }


    override fun successResult(boolean: Boolean) {}
    override fun failedResult(throwable: Throwable) {}

}