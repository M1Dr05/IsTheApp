package com.github.midros.istheapp.ui.activities.register

import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import cn.pedant.SweetAlert.SweetAlertDialog
import com.github.midros.istheapp.R
import com.github.midros.istheapp.ui.activities.base.BaseActivity
import com.github.midros.istheapp.ui.activities.login.LoginActivity
import com.github.midros.istheapp.utils.ConstFun.startAndFinishActivity
import kotterknife.bindView
import javax.inject.Inject

/**
 * Created by luis rafael on 8/03/18.
 */
class RegisterActivity : BaseActivity(), InterfaceViewRegister {

    private val edtEmail: EditText by bindView(R.id.edit_register_email)
    private val edtPass: EditText by bindView(R.id.edit_register_password)
    private val edtPassRepeat: EditText by bindView(R.id.edit_register_repeat_password)
    private val btnSignUp: Button by bindView(R.id.btn_register_sign_up)

    @Inject
    lateinit var interactor: InterfaceInteractorRegister<InterfaceViewRegister>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        activityComponent!!.inject(this)
        interactor.onAttach(this)
        emailValidationObservable(edtEmail)
        passValidationObservable(edtPass)
        passValidationObservable(edtPassRepeat)
        signInValidationObservable(btnSignUp)
        onClickRegister()
    }

    override fun onDestroy() {
        super.onDestroy()
        interactor.onDetach()
        clearDisposable()
    }

    private fun onClickRegister() {
        btnSignUp.setOnClickListener {
            if (edtPass.text.toString() == edtPassRepeat.text.toString())
                interactor.signUpDisposable(edtEmail.text.toString(), edtPass.text.toString())
            else {
                edtPassRepeat.text.clear()
                edtPass.text.clear()
                showError(getString(R.string.register_pass_match))
            }
        }
    }

    override fun successResult(boolean: Boolean) {
        hideDialog()
        if (boolean) {
            showMessage(getString(R.string.login_success))
            startAndFinishActivity<LoginActivity>()
        } else {
            showError(getString(R.string.sign_up_failed_try_again_later))
        }
    }

    override fun failedResult(throwable: Throwable) {
        hideDialog()
        showDialog(SweetAlertDialog.ERROR_TYPE, R.string.ops, "${getString(R.string.sign_up_failed)} ${throwable.message}", android.R.string.ok) {
            setCanceledOnTouchOutside(true)
            show()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) startAndFinishActivity<LoginActivity>()
        return super.onKeyDown(keyCode, event)
    }

}