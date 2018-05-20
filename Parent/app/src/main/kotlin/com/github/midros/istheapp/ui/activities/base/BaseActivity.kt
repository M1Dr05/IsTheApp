package com.github.midros.istheapp.ui.activities.base

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import cn.pedant.SweetAlert.SweetAlertDialog
import com.github.midros.istheapp.R
import com.github.midros.istheapp.di.component.ActivityComponent
import com.github.midros.istheapp.di.component.DaggerActivityComponent
import com.github.midros.istheapp.di.module.ActivityModule
import com.github.midros.istheapp.utils.ConstFun.alertDialog
import com.github.midros.istheapp.utils.ConstFun.openAppSystemSettings
import com.jakewharton.rxbinding2.widget.RxTextView
import com.pawegio.kandroid.longToast
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction

/**
 * Created by luis rafael on 7/03/18.
 */
abstract class BaseActivity : AppCompatActivity(), InterfaceView {

    private var alertDialog: SweetAlertDialog? = null
    private var compositeDisposable: CompositeDisposable? = null
    private var rxPermissions: RxPermissions? = null

    companion object {
        @JvmStatic
        var activityComponent: ActivityComponent? = null
    }

    private lateinit var emailObservable: Observable<Boolean>
    private lateinit var passObservable: Observable<Boolean>
    private lateinit var signInEnabled: Observable<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeActivityComponent()
    }

    override fun onResume() {
        super.onResume()
        initializeActivityComponent()
    }

    private fun initializeActivityComponent() {
        activityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this)).build()
        compositeDisposable = CompositeDisposable()
        rxPermissions = RxPermissions(this)
    }

    override fun getComponent(): ActivityComponent? = activityComponent

    override fun getPermissions(): RxPermissions? = rxPermissions

    override fun subscribePermission(permission: Permission, msgRationale: String, msgDenied: String, granted: () -> Unit) {
        when {
            permission.granted -> granted()
            permission.shouldShowRequestPermissionRationale -> showDialog(SweetAlertDialog.WARNING_TYPE, R.string.title_dialog, msgRationale, android.R.string.ok) {
                setCanceledOnTouchOutside(true)
                show()
            }
            else -> showDialog(SweetAlertDialog.WARNING_TYPE, R.string.title_dialog, msgDenied, R.string.go_to_setting, true) {
                setConfirmClickListener { openAppSystemSettings() }
                show()
            }
        }
    }


    override fun showDialog(alertType: Int, title: Int, msg: String?, txtPositiveButton: Int?, cancellable: Boolean, action: SweetAlertDialog.() -> Unit) {
        alertDialog = alertDialog(alertType, title, msg, txtPositiveButton, cancellable, action)
    }

    override fun hideDialog() {
        if (alertDialog != null) alertDialog!!.dismissWithAnimation()
    }


    override fun showError(message: String) {
        showMessage(message)
    }

    override fun showMessage(message: String) {
        longToast(message)
    }

    override fun addDisposable(disposable: Disposable) {
        compositeDisposable!!.add(disposable)
    }

    override fun clearDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable!!.dispose()
            compositeDisposable!!.clear()
        }
    }

    /** Email validation */
    fun emailValidationObservable(edtEmail: EditText) {
        emailObservable = RxTextView.textChanges(edtEmail).map { textEmail -> Patterns.EMAIL_ADDRESS.matcher(textEmail).matches() }
        emailObservable(edtEmail)
    }

    private fun emailObservable(edtEmail: EditText) {
        emailObservable.distinctUntilChanged().map { b -> if (b) R.drawable.ic_user else R.drawable.ic_user_alert }
                .subscribe { id -> edtEmail.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0) }
    }

    /** Password validation */
    fun passValidationObservable(edtPass: EditText) {
        passObservable = RxTextView.textChanges(edtPass).map { textPass -> textPass.length > 5 }
        passObservable(edtPass)
    }

    private fun passObservable(edtPass: EditText) {
        passObservable.distinctUntilChanged().map { b -> if (b) R.drawable.ic_lock_open else R.drawable.ic_lock }
                .subscribe { id -> edtPass.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0) }
    }

    /** Sign In observer */
    fun signInValidationObservable(btnSignIn: Button) {
        signInEnabled = Observable.combineLatest(emailObservable, passObservable, BiFunction { email, pass -> email && pass })
        signInEnableObservable(btnSignIn)
    }

    private fun signInEnableObservable(btnSignIn: Button) {
        signInEnabled.distinctUntilChanged().subscribe { enabled -> btnSignIn.isEnabled = enabled }
        signInEnabled.distinctUntilChanged()
                .map { b -> if (b) R.color.colorAccent else R.color.colorTextDisabled }
                .subscribe { color -> btnSignIn.backgroundTintList = ContextCompat.getColorStateList(this, color) }

    }

    override fun successResult(boolean: Boolean) {}
    override fun failedResult(throwable: Throwable) {}

}

