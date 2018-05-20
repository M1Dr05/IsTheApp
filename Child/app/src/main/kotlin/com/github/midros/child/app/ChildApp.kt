package com.github.midros.child.app

import android.app.Application
import android.arch.lifecycle.LifecycleObserver
import com.github.midros.child.di.component.AppComponent
import com.github.midros.child.di.component.DaggerAppComponent
import com.github.midros.child.di.module.AppModule
import com.github.midros.child.di.module.FirebaseModule

/**
 * Created by luis rafael on 13/03/18.
 */
class ChildApp : Application(), LifecycleObserver {

    companion object {
        @JvmStatic
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .firebaseModule(FirebaseModule())
                .build()
        appComponent.inject(this)
    }


}