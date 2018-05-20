package com.github.midros.istheapp.data.preference

import android.content.Context
import androidx.core.content.edit

/**
 * Created by luis rafael on 28/03/18.
 */
object DataSharePreference{

    fun Context.setStateAlertShow(state:Boolean) =
            getSharedPreferences("alertShow",Context.MODE_PRIVATE).edit { putBoolean("state",state) }

    fun Context.getStateAlertShow() : Boolean =
            getSharedPreferences("alertShow",Context.MODE_PRIVATE).getBoolean("state",false)

    fun Context.setLockPin(pin:String) =
            getSharedPreferences("lock",Context.MODE_PRIVATE).edit{ putString("pin",pin) }

    fun Context.getLockPin(): String =
            getSharedPreferences("lock",Context.MODE_PRIVATE).getString("pin","")

    fun Context.setLockState(state: Boolean) =
            getSharedPreferences("lockState",Context.MODE_PRIVATE).edit { putBoolean("state",state) }

    fun Context.getLockState() : Boolean =
            getSharedPreferences("lockState",Context.MODE_PRIVATE).getBoolean("state",false)

    fun Context.setFinishAppState(state: Boolean) =
            getSharedPreferences("finishAppState",Context.MODE_PRIVATE).edit { putBoolean("state",state) }

    fun Context.getFinishAppState() : Boolean =
            getSharedPreferences("finishAppState",Context.MODE_PRIVATE).getBoolean("state",true)

    fun Context.setTimeFinishApp(time:Int) =
            getSharedPreferences("timeFinishApp",Context.MODE_PRIVATE).edit { putInt("time",time) }

    fun Context.getTimeFinishApp() : Int =
            getSharedPreferences("timeFinishApp",Context.MODE_PRIVATE).getInt("time",1000)

}