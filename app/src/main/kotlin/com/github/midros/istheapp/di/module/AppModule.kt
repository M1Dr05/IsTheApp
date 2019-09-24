package com.github.midros.istheapp.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Created by luis rafael on 13/03/18.
 */
@Module
class AppModule(val app : Application){

    @Provides
    fun provideContext(): Context = app.applicationContext

}