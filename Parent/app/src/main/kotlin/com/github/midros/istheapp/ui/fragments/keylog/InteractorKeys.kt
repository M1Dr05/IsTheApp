package com.github.midros.istheapp.ui.fragments.keylog

import android.content.Context
import android.support.v4.app.FragmentManager
import com.github.midros.istheapp.data.rxFirebase.InterfaceFirebase
import com.github.midros.istheapp.ui.activities.base.BaseInteractor
import com.github.midros.istheapp.ui.fragments.keylog.adapter.InterfaceKeysAdapter
import com.github.midros.istheapp.ui.fragments.keylog.adapter.KeysRecyclerAdapter
import com.github.midros.istheapp.utils.Consts.CHILD_SERVICE_DATA
import com.github.midros.istheapp.utils.Consts.DATA
import com.github.midros.istheapp.utils.Consts.KEY_LOGGER
import com.google.firebase.database.DatabaseError
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by luis rafael on 18/03/18.
 */
class InteractorKeys<V : InterfaceViewKeys> @Inject constructor(supportFragment: FragmentManager, context: Context, firebase: InterfaceFirebase) : BaseInteractor<V>(supportFragment, context, firebase), InterfaceInteractorKeys<V>, InterfaceKeysAdapter {

    private var recyclerAdapter: KeysRecyclerAdapter? = null

    override fun setRecyclerAdapter() {
        recyclerAdapter = KeysRecyclerAdapter(firebase().getDatabaseReference("$KEY_LOGGER/$DATA").limitToLast(300))
        getView()!!.setRecyclerAdapter(recyclerAdapter!!)
        recyclerAdapter!!.onRecyclerAdapterListener(this)
    }

    override fun startRecyclerAdapter() {
        if (recyclerAdapter != null) recyclerAdapter!!.startListening()
    }

    override fun stopRecyclerAdapter() {
        if (recyclerAdapter != null) recyclerAdapter!!.stopListening()
    }

    override fun successResult(boolean: Boolean) {
        if (getView() != null) getView()!!.successResult(boolean)
    }

    override fun failedResult(error: DatabaseError) {
        if (getView() != null) getView()!!.failedResult(Throwable(error.message.toString()))
    }

    override fun valueEventEnableKeys() {
        getView()!!.addDisposable(firebase().valueEvent("$DATA/$CHILD_SERVICE_DATA")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { if (getView() != null) getView()!!.setValueState(it) })
    }

}