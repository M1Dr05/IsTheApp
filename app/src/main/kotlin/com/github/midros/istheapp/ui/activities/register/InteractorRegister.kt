package com.github.midros.istheapp.ui.activities.register

import android.content.Context
import androidx.fragment.app.FragmentManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.github.midros.istheapp.R
import com.github.midros.istheapp.data.rxFirebase.InterfaceFirebase
import com.github.midros.istheapp.ui.activities.base.BaseInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by luis rafael on 10/03/18.
 */
class InteractorRegister<V : InterfaceViewRegister> @Inject constructor(supportFragment: FragmentManager, context: Context, firebase: InterfaceFirebase) : BaseInteractor<V>(supportFragment, context, firebase), InterfaceInteractorRegister<V> {

    override fun signUpDisposable(email: String, pass: String) {
        getView()!!.addDisposable(firebase().signUp(email, pass)
                .map { authResult -> authResult.user != null }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { getView()!!.showDialog(SweetAlertDialog.PROGRESS_TYPE, R.string.logging_in, null, null) { show() } }
                .subscribe({ getView()!!.successResult(it) }, { getView()!!.failedResult(it) }))
    }

}