package com.github.midros.child.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.github.midros.child.services.sms.SmsService
import com.github.midros.child.utils.Consts.SMS_ADDRESS
import com.github.midros.child.utils.Consts.SMS_BODY


/**
 * Created by luis rafael on 13/03/18.
 */
class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        var smsAddress = ""
        var smsBody = ""

        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION){
            for (smsMessage in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                smsAddress = smsMessage.displayOriginatingAddress
                smsBody += smsMessage.messageBody
            }
            context.setIntent(smsAddress,smsBody)
        }
    }

    private fun Context.setIntent(smsAddress:String,smsBody:String){
        val myIntent = Intent(this, SmsService::class.java)
        myIntent.putExtra(SMS_ADDRESS,smsAddress)
        myIntent.putExtra(SMS_BODY,smsBody)
        startService(myIntent)
    }

}