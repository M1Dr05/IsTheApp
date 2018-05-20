package com.github.midros.child.di.module

import android.app.Service
import android.content.Context
import com.github.midros.child.di.PerService
import com.github.midros.child.services.calls.InteractorCalls
import com.github.midros.child.services.calls.InterfaceInteractorCalls
import com.github.midros.child.services.calls.InterfaceServiceCalls
import com.github.midros.child.services.sms.InteractorSms
import com.github.midros.child.services.sms.InterfaceInteractorSms
import com.github.midros.child.services.sms.InterfaceServiceSms
import dagger.Module
import dagger.Provides

/**
 * Created by luis rafael on 13/03/18.
 */
@Module
class ServiceModule(var service:Service) {

    @Provides
    fun provideContext(): Context = service.applicationContext

    @Provides
    @PerService
    fun provideInterfaceInteractorCalls(interactor: InteractorCalls<InterfaceServiceCalls>): InterfaceInteractorCalls<InterfaceServiceCalls> = interactor

    @Provides
    @PerService
    fun provideInterfaceInteractorSms(interactor: InteractorSms<InterfaceServiceSms>): InterfaceInteractorSms<InterfaceServiceSms> = interactor

}