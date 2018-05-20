package com.github.midros.istheapp.ui.activities.login

import android.os.Bundle
import android.support.multidex.MultiDex
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.github.midros.istheapp.R
import com.github.midros.istheapp.ui.activities.base.BaseActivity
import com.github.midros.istheapp.ui.activities.main.MainActivity
import com.github.midros.istheapp.ui.activities.register.RegisterActivity
import com.github.midros.istheapp.utils.ConstFun.startAndFinishActivity
import kotterknife.bindView
import javax.inject.Inject


/**
 * Created by luis rafael on 7/03/18.
 */
class LoginActivity : BaseActivity(), InterfaceViewLogin {

    private val edtEmail: EditText by bindView(R.id.edit_login_email)
    private val edtPass: EditText by bindView(R.id.edit_login_password)
    private val btnSignIn: Button by bindView(R.id.btn_login_signin)
    private val btnSignUp: TextView by bindView(R.id.txt_login_signup)

    @Inject
    lateinit var interactor: InterfaceInteractorLogin<InterfaceViewLogin>

    override fun onStart() {
        super.onStart()
        if (interactor.user() != null) startAndFinishActivity<MainActivity>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        MultiDex.install(this)
        activityComponent!!.inject(this)
        interactor.onAttach(this)

        emailValidationObservable(edtEmail)
        passValidationObservable(edtPass)
        signInValidationObservable(btnSignIn)
        onClickLogin()
    }

    override fun onDestroy() {
        interactor.onDetach()
        clearDisposable()
        super.onDestroy()
    }

    private fun onClickLogin() {
        btnSignUp.setOnClickListener {
            startAndFinishActivity<RegisterActivity>()
        }
        btnSignIn.setOnClickListener {
            interactor.signInDisposable(edtEmail.text.toString(), edtPass.text.toString())
        }
    }

    override fun successResult(boolean: Boolean) {
        hideDialog()
        if (boolean) {
            showMessage(getString(R.string.login_success))
            startAndFinishActivity<MainActivity>()
        } else {
            showError(getString(R.string.login_failed_try_again_later))
        }
    }

    override fun failedResult(throwable: Throwable) {
        hideDialog()
        showDialog(SweetAlertDialog.ERROR_TYPE, R.string.ops, "${getString(R.string.login_failed)} ${throwable.message}", android.R.string.ok) {
            setCanceledOnTouchOutside(true)
            show()
        }
    }


}