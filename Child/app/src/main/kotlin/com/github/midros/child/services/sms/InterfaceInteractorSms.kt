package com.github.midros.child.services.sms

import com.github.midros.child.di.PerService
import com.github.midros.child.services.base.InterfaceInteractorService

/**
 * Created by luis rafael on 27/03/18.
 */
@PerService
interface InterfaceInteractorSms<S : InterfaceServiceSms> : InterfaceInteractorService<S> {

    fun setPushSms(smsAddress: String, smsBody: String)

}