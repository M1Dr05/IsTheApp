package com.github.midros.child.ui.login

import android.Manifest.permission.*
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import com.github.midros.child.R
import com.github.midros.child.ui.main.MainActivity
import com.github.midros.child.ui.base.BaseActivity
import com.github.midros.child.utils.ConstFun.startAndFinishActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import kotterknife.bindView
import javax.inject.Inject

/**
 * Created by luis rafael on 13/03/18.
 */
class LoginActivity : BaseActivity() , InterfaceViewLogin{

    private var permission: RxPermissions?=null

    private val edtEmail: EditText by bindView(R.id.edit_login_email)
    private val edtPass: EditText by bindView(R.id.edit_login_password)
    private val btnSignIn: Button by bindView(R.id.btn_login_signin)

    @Inject lateinit var interactor:InterfaceInteractorLogin<InterfaceViewLogin>

    private lateinit var emailObservable: Observable<Boolean>
    private lateinit var passObservable: Observable<Boolean>
    private lateinit var signInEnabled: Observable<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        getComponent()!!.inject(this)
        interactor.onAttach(this)
        permission = RxPermissions(this)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        emailValidationObservable()
        passValidationObservable()
        signInValidationObservable()
        onClickLogin()
    }

    override fun onStart() {
        super.onStart()
        if (interactor.user()!=null) startAndFinishActivity<MainActivity>()
    }

    private fun onClickLogin(){
        btnSignIn.setOnClickListener {
            permission!!.requestEachCombined(READ_CONTACTS, ACCESS_FINE_LOCATION, RECORD_AUDIO, READ_PHONE_STATE, WRITE_EXTERNAL_STORAGE,RECEIVE_SMS, CAMERA)
                    .subscribe {permission -> subscribePermission(permission,R.string.message_permission,R.string.message_permission_never_ask_again){
                        sigIn()
                    }}
        }
    }

    private fun sigIn(){
        interactor.signInDisposable(edtEmail.text.toString(),edtPass.text.toString())
    }

    override fun successResult(boolean: Boolean) {
        if (boolean){
            startAndFinishActivity<MainActivity>()
        }else{
            showError(getString(R.string.login_failed_try_again_later))
        }
    }

    override fun failedResult(throwable: Throwable) {
        showError("${getString(R.string.login_failed)} ${throwable.message}")
    }

    override fun onDestroy() {
        super.onDestroy()
        interactor.onDetach()
        clearDisposable()
    }

    /** Email validation */
    private fun emailValidationObservable(){
        emailObservable = RxTextView.textChanges(edtEmail).map { textEmail -> Patterns.EMAIL_ADDRESS.matcher(textEmail).matches() }
        emailObservable()
    }

    private fun emailObservable(){
        emailObservable.distinctUntilChanged().map { b -> if (b) R.drawable.ic_user else R.drawable.ic_user_alert }
                .subscribe { id -> edtEmail.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0) }
    }

    /** Password validation */
    private fun passValidationObservable(){
        passObservable = RxTextView.textChanges(edtPass).map { textPass -> textPass.length > 5 }
        passObservable()
    }

    private fun passObservable(){
        passObservable.distinctUntilChanged().map { b -> if (b) R.drawable.ic_lock_open else R.drawable.ic_lock }
                .subscribe { id -> edtPass.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0) }
    }

    /** Sign In observer */
    private fun signInValidationObservable(){
        signInEnabled = Observable.combineLatest(emailObservable, passObservable, BiFunction { email, pass -> email && pass })
        signInEnableObservable()
    }

    private fun signInEnableObservable(){
        signInEnabled.distinctUntilChanged().subscribe { enabled -> btnSignIn.isEnabled = enabled }
        signInEnabled.distinctUntilChanged()
                .map { b -> if (b) R.color.colorAccent else R.color.colorTextDisabled }
                .subscribe { color -> btnSignIn.backgroundTintList = ContextCompat.getColorStateList(this, color) }

    }
}