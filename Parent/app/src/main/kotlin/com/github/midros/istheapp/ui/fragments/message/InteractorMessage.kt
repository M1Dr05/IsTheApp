package com.github.midros.istheapp.ui.fragments.message

import android.content.Context
import android.support.v4.app.FragmentManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.github.midros.istheapp.R
import com.github.midros.istheapp.data.rxFirebase.InterfaceFirebase
import com.github.midros.istheapp.ui.activities.base.BaseInteractor
import com.github.midros.istheapp.ui.fragments.message.adapter.InterfaceSmsAdapter
import com.github.midros.istheapp.ui.fragments.message.adapter.SmsRecyclerAdapter
import com.github.midros.istheapp.utils.Consts.DATA
import com.github.midros.istheapp.utils.Consts.SMS
import com.google.firebase.database.DatabaseError
import javax.inject.Inject

/**
 * Created by luis rafael on 20/03/18.
 */
class InteractorMessage<V : InterfaceViewMessage> @Inject constructor(supportFragment: FragmentManager, context: Context, firebase: InterfaceFirebase) : BaseInteractor<V>(supportFragment, context, firebase), InterfaceInteractorMessage<V>, InterfaceSmsAdapter {

    private var recyclerAdapter: SmsRecyclerAdapter? = null

    override fun setRecyclerAdapter() {
        recyclerAdapter = SmsRecyclerAdapter(firebase().getDatabaseReference("$SMS/$DATA").limitToLast(300))
        getView()!!.setRecyclerAdapter(recyclerAdapter!!)
        recyclerAdapter!!.onRecyclerAdapterListener(this)
    }

    override fun startRecyclerAdapter() {
        if (recyclerAdapter != null) recyclerAdapter!!.startListening()
    }

    override fun stopRecyclerAdapter() {
        if (recyclerAdapter != null) recyclerAdapter!!.stopListening()
    }

    override fun valueEvent() {}

    override fun successResult(boolean: Boolean) {
        if (getView() != null) getView()!!.successResult(boolean)
    }

    override fun failedResult(error: DatabaseError) {
        if (getView() != null) getView()!!.failedResult(Throwable(error.message.toString()))
    }

    override fun onLongClickSms(keySms: String) {
        getView()!!.showDialog(SweetAlertDialog.WARNING_TYPE, R.string.title_dialog, getContext().getString(R.string.message_dialog_delete_sms),
                R.string.delete, true) {
            setConfirmClickListener {
                firebase().getDatabaseReference("$SMS/$DATA/$keySms").removeValue()
                getView()!!.hideDialog()
            }
            show()
        }
    }

}