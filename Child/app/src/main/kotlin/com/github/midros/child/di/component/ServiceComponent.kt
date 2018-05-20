package com.github.midros.child.di.component

import com.github.midros.child.di.PerService
import com.github.midros.child.di.module.ServiceModule
import com.github.midros.child.services.calls.CallsService
import com.github.midros.child.services.sms.SmsService
import com.github.midros.child.services.social.MonitorService
import dagger.Component

/**
 * Created by luis rafael on 13/03/18.
 */
@PerService
@Component(dependencies = [AppComponent::class], modules = [ServiceModule::class])
interface ServiceComponent {

    fun inject(callsService: CallsService)
    fun inject(smsService: SmsService)
    fun inject(monitorService: MonitorService)

}