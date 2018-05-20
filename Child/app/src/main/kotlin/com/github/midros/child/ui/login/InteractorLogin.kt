package com.github.midros.child.ui.login

import android.content.Context
import com.github.midros.child.R
import com.github.midros.child.data.rxFirebase.InterfaceFirebase
import com.github.midros.child.ui.base.BaseInteractor
import javax.inject.Inject

/**
 * Created by luis rafael on 9/03/18.
 */
class InteractorLogin<V : InterfaceViewLogin> @Inject constructor(context: Context, firebase: InterfaceFirebase) : BaseInteractor<V>(context, firebase), InterfaceInteractorLogin<V> {

    override fun signInDisposable(email: String, pass: String) {
        getView().addDisposable(firebase().signIn(email, pass)
                .map { authResult -> authResult.user != null }
                .doOnSubscribe { getView().showLoading(getContext().getString(R.string.logging_in)) }
                .doFinally { getView().hideLoading() }
                .subscribe({ getView().successResult(it) }, { getView().failedResult(it) }))
    }
}