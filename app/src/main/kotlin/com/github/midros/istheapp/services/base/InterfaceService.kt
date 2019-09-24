package com.github.midros.istheapp.services.base

import com.github.midros.istheapp.di.component.ServiceComponent
import io.reactivex.disposables.Disposable

/**
 * Created by luis rafael on 22/03/18.
 */
interface InterfaceService {

    fun getComponent(): ServiceComponent?

    fun addDisposable(disposable: Disposable)

    fun clearDisposable()

}