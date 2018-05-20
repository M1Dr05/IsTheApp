package com.github.midros.child.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import com.github.midros.child.services.calls.CallsService
import com.github.midros.child.utils.Consts.COMMAND_TYPE
import com.github.midros.child.utils.Consts.PHONE_NUMBER
import com.github.midros.child.utils.Consts.STATE_CALL_END
import com.github.midros.child.utils.Consts.STATE_CALL_START
import com.github.midros.child.utils.Consts.STATE_INCOMING_NUMBER

/**
 * Created by luis rafael on 13/03/18.
 */
class CallsReceiver : BroadcastReceiver() {

    private var phoneNumber: String? = null
    private var extraState: String? = null

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_NEW_OUTGOING_CALL || intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
            extraState = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            context.startCallService(intent)
        }
    }

    private fun Context.startCallService(intent: Intent) {
        if (extraState != null) {
            when (extraState) {
                TelephonyManager.EXTRA_STATE_OFFHOOK -> setIntentType(STATE_CALL_START)
                TelephonyManager.EXTRA_STATE_IDLE -> setIntentType(STATE_CALL_END)
                TelephonyManager.EXTRA_STATE_RINGING -> {
                    if (phoneNumber == null) phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
                    setIntent()
                }
            }
        } else if (phoneNumber != null) setIntent()
    }

    private fun Context.setIntentType(type: Int) {
        val myIntent = Intent(this, CallsService::class.java)
        myIntent.putExtra(COMMAND_TYPE, type)
        startService(myIntent)
    }

    private fun Context.setIntent() {
        val myIntent = Intent(this, CallsService::class.java)
        myIntent.putExtra(COMMAND_TYPE, STATE_INCOMING_NUMBER)
        myIntent.putExtra(PHONE_NUMBER, phoneNumber)
        startService(myIntent)
    }
}