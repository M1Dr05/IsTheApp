package com.github.midros.istheapp.ui.activities.main

import com.github.midros.istheapp.di.PerActivity
import com.github.midros.istheapp.ui.activities.base.InterfaceInteractor
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference

/**
 * Created by luis rafael on 9/03/18.
 */
@PerActivity
interface InterfaceInteractorMain<V : InterfaceViewMain> : InterfaceInteractor<V> {

    fun signOut()
    fun getDatabaseReference(child: String): DatabaseReference
    fun getStorageReference(child: String): StorageReference
    fun setCountDownTimer()
    fun restartCountDownTimer()
    fun cancelCountDownTimer()

}