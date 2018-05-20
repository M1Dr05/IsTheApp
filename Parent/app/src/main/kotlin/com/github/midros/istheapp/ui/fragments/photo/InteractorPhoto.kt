package com.github.midros.istheapp.ui.fragments.photo

import android.content.Context
import android.support.v4.app.FragmentManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.github.midros.istheapp.R
import com.github.midros.istheapp.data.model.ChildPhoto
import com.github.midros.istheapp.data.rxFirebase.InterfaceFirebase
import com.github.midros.istheapp.ui.activities.base.BaseInteractor
import com.github.midros.istheapp.ui.fragments.photo.adapter.InterfacePhotoAdapter
import com.github.midros.istheapp.ui.fragments.photo.adapter.PhotoRecyclerAdapter
import com.github.midros.istheapp.utils.Consts.CHILD_PERMISSION
import com.github.midros.istheapp.utils.Consts.CHILD_SERVICE_DATA
import com.github.midros.istheapp.utils.Consts.DATA
import com.github.midros.istheapp.utils.Consts.PARAMS
import com.github.midros.istheapp.utils.Consts.PHOTO
import com.google.firebase.database.DatabaseError
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by luis rafael on 20/03/18.
 */
class InteractorPhoto<V : InterfaceViewPhoto> @Inject constructor(supportFragment: FragmentManager, context: Context, firebase: InterfaceFirebase) : BaseInteractor<V>(supportFragment, context, firebase), InterfaceInteractorPhoto<V>, InterfacePhotoAdapter {

    private var recyclerAdapter: PhotoRecyclerAdapter? = null

    override fun setRecyclerAdapter() {
        recyclerAdapter = PhotoRecyclerAdapter(firebase().getDatabaseReference("$PHOTO/$DATA").limitToLast(300))
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

    override fun getPhotoCamera(facing: Int) {
        val childPhoto = ChildPhoto(true, facing)
        firebase().getDatabaseReference("$PHOTO/$PARAMS").setValue(childPhoto)
    }

    override fun onLongClickDeleteFilePhoto(keyFileName: String, childName: String) {
        getView()!!.showDialog(SweetAlertDialog.WARNING_TYPE, R.string.title_dialog, getContext().getString(R.string.message_dialog_delete_photo),
                R.string.delete, true) {
            setConfirmClickListener {
                firebase().getDatabaseReference("$PHOTO/$DATA/$keyFileName").removeValue()
                firebase().getStorageReference("$PHOTO/$childName").delete()
                getView()!!.hideDialog()
            }
            show()
        }
    }

    override fun valueEventEnablePhoto() {
        getView()!!.addDisposable(firebase().valueEvent("$DATA/$CHILD_SERVICE_DATA")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { if (getView() != null) getView()!!.setValueState(it) })
    }

    override fun valueEventEnablePermission() {
        getView()!!.addDisposable(firebase().valueEvent("$PHOTO/$CHILD_PERMISSION")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { if (getView() != null) getView()!!.setValuePermission(it) })
    }

}