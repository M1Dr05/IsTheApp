package com.github.midros.istheapp.utils.async

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import cn.pedant.SweetAlert.SweetAlertDialog
import com.github.midros.istheapp.R
import com.github.midros.istheapp.app.IsTheApp
import com.github.midros.istheapp.utils.ConstFun.alertDialog

/**
 * Created by luis rafael on 05/06/19.
 */
@SuppressLint("StaticFieldLeak")
class AsyncTaskRootPermission(private val context: Context,private val result:(result:Boolean)->Unit) : AsyncTask<String, Boolean, Boolean>() {

    private var dialog :SweetAlertDialog?=null

    override fun onPreExecute() {
        super.onPreExecute()
        dialog = context.alertDialog(SweetAlertDialog.PROGRESS_TYPE, R.string.check_root_access,null,0){
            show()
        }
    }

    override fun doInBackground(vararg params: String?): Boolean = IsTheApp.root.obtainPermission()

    override fun onPostExecute(result: Boolean) {
        super.onPostExecute(result)
        if (dialog!=null) dialog!!.dismiss()
        result(result)
    }


}