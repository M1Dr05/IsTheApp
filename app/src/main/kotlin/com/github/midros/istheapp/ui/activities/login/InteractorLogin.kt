package com.github.midros.istheapp.ui.activities.login

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
 * Created by luis rafael on 9/03/18.
 */
class InteractorLogin<V : InterfaceViewLogin> @Inject constructor(supportFragment: FragmentManager, context: Context, firebase: InterfaceFirebase) : BaseInteractor<V>(supportFragment, context, firebase), InterfaceInteractorLogin<V> {

    override fun signInDisposable(email: String, pass: String) {
        getView()!!.addDisposable(firebase().signIn(email, pass)
                .map { authResult -> authResult.user != null }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {if (isNullView()) getView()!!.showDialog(SweetAlertDialog.PROGRESS_TYPE, R.string.logging_in, null, null) { show() } }
                .subscribe({ if (isNullView()) getView()!!.successResult(it) }, {if (isNullView()) getView()!!.failedResult(it) }))
    }

}