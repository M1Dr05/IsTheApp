package com.github.midros.child.di.component

import com.github.midros.child.app.ChildApp
import com.github.midros.child.data.rxFirebase.InterfaceFirebase
import com.github.midros.child.di.module.AppModule
import com.github.midros.child.di.module.FirebaseModule
import com.github.midros.child.services.accessibilityData.AccessibilityDataService
import dagger.Component
import javax.inject.Singleton

/**
 * Created by luis rafael on 13/03/18.
 */
@Singleton
@Component(modules = [AppModule::class, FirebaseModule::class])
interface AppComponent {

    fun inject(app: ChildApp)
    fun inject(accessibilityDataService: AccessibilityDataService)
    fun getInterfaceFirebase(): InterfaceFirebase

}