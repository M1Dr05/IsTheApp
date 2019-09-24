package com.github.midros.istheapp.ui.fragments.notifications

import com.github.midros.istheapp.ui.activities.base.InterfaceInteractor

/**
 * Created by luis rafael on 17/03/19.
 */
interface InterfaceInteractorNotifyMessage<V : InterfaceViewNotifyMessage> : InterfaceInteractor<V> {
    fun valueEventEnableNotifications()
}