package com.github.midros.istheapp.di.module

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.github.midros.istheapp.di.PerActivity
import com.github.midros.istheapp.ui.activities.login.InteractorLogin
import com.github.midros.istheapp.ui.activities.login.InterfaceInteractorLogin
import com.github.midros.istheapp.ui.activities.login.InterfaceViewLogin
import com.github.midros.istheapp.ui.activities.main.InteractorMain
import com.github.midros.istheapp.ui.activities.main.InterfaceInteractorMain
import com.github.midros.istheapp.ui.activities.main.InterfaceViewMain
import com.github.midros.istheapp.ui.activities.register.InteractorRegister
import com.github.midros.istheapp.ui.activities.register.InterfaceInteractorRegister
import com.github.midros.istheapp.ui.activities.register.InterfaceViewRegister
import com.github.midros.istheapp.ui.activities.main.PagerAdapterFragment
import com.github.midros.istheapp.ui.fragments.calls.InteractorCalls
import com.github.midros.istheapp.ui.fragments.calls.InterfaceInteractorCalls
import com.github.midros.istheapp.ui.fragments.calls.InterfaceViewCalls
import com.github.midros.istheapp.ui.fragments.photo.InteractorPhoto
import com.github.midros.istheapp.ui.fragments.photo.InterfaceInteractorPhoto
import com.github.midros.istheapp.ui.fragments.photo.InterfaceViewPhoto
import com.github.midros.istheapp.ui.fragments.keylog.InteractorKeys
import com.github.midros.istheapp.ui.fragments.keylog.InterfaceInteractorKeys
import com.github.midros.istheapp.ui.fragments.keylog.InterfaceViewKeys
import com.github.midros.istheapp.ui.fragments.maps.InteractorMaps
import com.github.midros.istheapp.ui.fragments.maps.InterfaceInteractorMaps
import com.github.midros.istheapp.ui.fragments.maps.InterfaceViewMaps
import com.github.midros.istheapp.ui.fragments.message.InteractorMessage
import com.github.midros.istheapp.ui.fragments.message.InterfaceInteractorMessage
import com.github.midros.istheapp.ui.fragments.message.InterfaceViewMessage
import com.github.midros.istheapp.ui.fragments.social.InteractorSocial
import com.github.midros.istheapp.ui.fragments.social.InterfaceInteractorSocial
import com.github.midros.istheapp.ui.fragments.social.InterfaceViewSocial
import com.github.midros.istheapp.utils.Consts.FIELD_ONE
import com.github.midros.istheapp.utils.Consts.FIELD_TWO
import com.google.android.gms.maps.SupportMapFragment
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * Created by luis rafael on 8/03/18.
 */
@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    fun provideContext(): Context = activity.applicationContext

    @Provides
    fun provideActivity(): AppCompatActivity = activity

    @Provides
    fun provideSupportFragmentManager(): FragmentManager = activity.supportFragmentManager

    @Provides
    fun provideFragmentPagerAdapter(context: Context, fragmentManager: FragmentManager): PagerAdapterFragment = PagerAdapterFragment(context, fragmentManager)

    @Provides
    fun provideSupportMapFragment(): SupportMapFragment = SupportMapFragment.newInstance()

    @Provides
    @Named(FIELD_ONE)
    fun provideGridLayoutManager(context: Context): GridLayoutManager = GridLayoutManager(context, 1)

    @Provides
    @Named(FIELD_TWO)
    fun provideGridLayoutManagerTwo(context: Context): GridLayoutManager = GridLayoutManager(context, 2)

    @Provides
    @PerActivity
    fun provideInterfaceInteractorMain(interactor: InteractorMain<InterfaceViewMain>): InterfaceInteractorMain<InterfaceViewMain> = interactor

    @Provides
    @PerActivity
    fun provideInterfaceInteractorLogin(interactor: InteractorLogin<InterfaceViewLogin>): InterfaceInteractorLogin<InterfaceViewLogin> = interactor

    @Provides
    @PerActivity
    fun provideInterfaceInteractorRegister(interactor: InteractorRegister<InterfaceViewRegister>): InterfaceInteractorRegister<InterfaceViewRegister> = interactor

    @Provides
    @PerActivity
    fun provideInterfaceInteractorMaps(interactor: InteractorMaps<InterfaceViewMaps>): InterfaceInteractorMaps<InterfaceViewMaps> = interactor

    @Provides
    @PerActivity
    fun provideInterfaceInteractorCalls(interactor: InteractorCalls<InterfaceViewCalls>): InterfaceInteractorCalls<InterfaceViewCalls> = interactor

    @Provides
    @PerActivity
    fun provideInterfaceInteractorKeys(interactor: InteractorKeys<InterfaceViewKeys>): InterfaceInteractorKeys<InterfaceViewKeys> = interactor

    @Provides
    @PerActivity
    fun provideInterfaceInteractorMessage(interactor: InteractorMessage<InterfaceViewMessage>): InterfaceInteractorMessage<InterfaceViewMessage> = interactor

    @Provides
    @PerActivity
    fun provideInterfaceInteractorPhoto(interactor: InteractorPhoto<InterfaceViewPhoto>): InterfaceInteractorPhoto<InterfaceViewPhoto> = interactor

    @Provides
    @PerActivity
    fun provideInterfaceInteractorSocial(interactor: InteractorSocial<InterfaceViewSocial>): InterfaceInteractorSocial<InterfaceViewSocial> = interactor

}