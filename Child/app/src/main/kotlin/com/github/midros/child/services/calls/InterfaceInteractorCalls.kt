package com.github.midros.child.services.calls

import com.github.midros.child.di.PerService
import com.github.midros.child.services.base.InterfaceInteractorService

/**
 * Created by luis rafael on 27/03/18.
 */
@PerService
interface InterfaceInteractorCalls<S : InterfaceServiceCalls> : InterfaceInteractorService<S> {

    fun startRecording(phoneNumber:String?)
    fun stopRecording()

}