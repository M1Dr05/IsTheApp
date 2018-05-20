package com.github.midros.istheapp.ui.fragments.social

import com.github.midros.istheapp.di.PerActivity
import com.github.midros.istheapp.ui.activities.base.InterfaceInteractor

/**
 * Created by luis rafael on 20/03/18.
 */
@PerActivity
interface InterfaceInteractorSocial<V : InterfaceViewSocial> : InterfaceInteractor<V> {

    fun valueEventSocial()
    fun valueEventEnablePermission()

}