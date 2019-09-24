package com.github.midros.istheapp.ui.fragments.calls

import com.github.midros.istheapp.di.PerActivity
import com.github.midros.istheapp.ui.activities.base.InterfaceInteractor

/**
 * Created by luis rafael on 12/03/18.
 */
@PerActivity
interface InterfaceInteractorCalls<V : InterfaceViewCalls> : InterfaceInteractor<V> {
    fun stopAudioCallHolder()
}