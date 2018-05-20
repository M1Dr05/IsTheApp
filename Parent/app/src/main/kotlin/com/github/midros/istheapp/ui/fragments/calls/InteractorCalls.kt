package com.github.midros.istheapp.ui.fragments.calls

import android.Manifest
import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import cn.pedant.SweetAlert.SweetAlertDialog
import com.github.midros.istheapp.R
import com.github.midros.istheapp.data.rxFirebase.InterfaceFirebase
import com.github.midros.istheapp.ui.activities.base.BaseInteractor
import com.github.midros.istheapp.ui.fragments.calls.adapter.CallsRecyclerAdapter
import com.github.midros.istheapp.ui.fragments.calls.adapter.CallsViewHolder
import com.github.midros.istheapp.ui.fragments.calls.adapter.InterfaceCallsAdapter
import com.github.midros.istheapp.utils.FileHelper.deleteFileName
import com.github.midros.istheapp.utils.Consts.CALLS
import com.github.midros.istheapp.utils.Consts.DATA
import com.google.firebase.database.DatabaseError
import com.pawegio.kandroid.toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import javax.inject.Inject

/**
 * Created by luis rafael on 12/03/18.
 */
class InteractorCalls<V: InterfaceViewCalls> @Inject constructor(supportFragment: FragmentManager, context: Context, firebase: InterfaceFirebase) : BaseInteractor<V>(supportFragment, context,firebase), InterfaceInteractorCalls<V>, InterfaceCallsAdapter {

    private var recyclerAdapter:CallsRecyclerAdapter?=null

    override fun setRecyclerAdapter() {
        recyclerAdapter = CallsRecyclerAdapter(firebase().getDatabaseReference("$CALLS/$DATA").limitToLast(300))
        getView()!!.setRecyclerAdapter(recyclerAdapter!!)
        recyclerAdapter!!.onRecyclerAdapterListener(this)
    }

    override fun startRecyclerAdapter() {
        if (recyclerAdapter!=null) recyclerAdapter!!.startListening()
    }

    override fun stopRecyclerAdapter() {
        if (recyclerAdapter!=null) recyclerAdapter!!.stopListening()
    }

    override fun stopAudioCallHolder() {
        if (recyclerAdapter!=null) recyclerAdapter!!.stopOldAudioCall()
    }

    override fun successResult(boolean: Boolean) {
        if (getView()!=null) getView()!!.successResult(boolean)
    }

    override fun failedResult(error: DatabaseError) {
        if (getView()!=null) getView()!!.failedResult(Throwable(error.message.toString()))
    }

    override fun onCheckPermissionAudioCalls(file: File, childName: String, fileName: String, holder: CallsViewHolder) {
        getView()!!.getPermissions()!!.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe {permission ->
                    getView()!!.subscribePermission(permission,getContext().getString(R.string.message_permission), getContext().getString(R.string.message_permission_never_ask_again)){
                        if (recyclerAdapter!=null) recyclerAdapter!!.onClickListener(holder,file,fileName,childName)
                    }
                }
    }

    override fun onClickDownloadAudioCall(file: File, childName: String, holder: CallsViewHolder) {
        getView()!!.addDisposable(firebase().getFile("$CALLS/$childName",file)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{ setShowDownloader(true,holder) }
                .subscribe({
                    setShowDownloader(false,holder)
                    setDrawableItemClick(R.drawable.ic_play_arrow_24dp,holder)
                },{
                    error ->
                    setShowDownloader(false,holder)
                    getContext().toast(error.message.toString())
                }))
    }

    override fun onLongClickDeleteFileCall(keyFileName:String,fileName: String,childName: String) {
        getView()!!.showDialog(SweetAlertDialog.WARNING_TYPE,R.string.title_dialog,getContext().getString(R.string.message_dialog_delete_call_audio),
                R.string.delete,true){
            setConfirmClickListener {
                firebase().getDatabaseReference("$CALLS/$DATA/$keyFileName").removeValue()
                firebase().getStorageReference("$CALLS/$childName").delete()
                context.deleteFileName(fileName)
                getView()!!.hideDialog()
            }
            show()
        }
    }

    private fun setShowDownloader(visibility:Boolean,holder: CallsViewHolder){
        if (visibility) holder.progressCall.spin()
        else holder.progressCall.stopSpinning()

    }

    private fun setDrawableItemClick(id:Int,holder: CallsViewHolder){
        holder.imageItem.background = ContextCompat.getDrawable(getContext(),id)
    }
}