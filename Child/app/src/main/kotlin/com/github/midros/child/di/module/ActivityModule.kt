package com.github.midros.child.di.module

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.github.midros.child.di.PerActivity
import com.github.midros.child.ui.login.InteractorLogin
import com.github.midros.child.ui.login.InterfaceInteractorLogin
import com.github.midros.child.ui.login.InterfaceViewLogin
import dagger.Module
import dagger.Provides

/**
 * Created by luis rafael on 13/03/18.
 */
@Module
class ActivityModule(var activity: AppCompatActivity) {

    @Provides
    fun provideContext(): Context = activity.applicationContext

    @Provides
    fun provideActivity(): AppCompatActivity = activity

    @Provides
    @PerActivity
    fun provideInterfaceInteractorLogin(interactor: InteractorLogin<InterfaceViewLogin>): InterfaceInteractorLogin<InterfaceViewLogin> = interactor


}