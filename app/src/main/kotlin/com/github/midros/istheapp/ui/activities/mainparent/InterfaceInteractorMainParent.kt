package com.github.midros.istheapp.ui.activities.mainparent

import com.github.midros.istheapp.di.PerActivity
import com.github.midros.istheapp.ui.activities.base.InterfaceInteractor
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import java.io.File

/**
 * Created by luis rafael on 9/03/18.
 */
@PerActivity
interface InterfaceInteractorMainParent<V : InterfaceViewMainParent> : InterfaceInteractor<V> {

    fun getUser() : FirebaseUser?
    fun signOut()
    fun getDatabaseReference(child: String): DatabaseReference
    fun getStorageReference(child: String): StorageReference
    fun valueAccounts(firstTime:Boolean)
    fun uploadPhotoChild(photo: File)
    fun initializeCountDownTimer()
    fun startCountDownTimer()
    fun restartCountDownTimer()
    fun cancelCountDownTimer()

    fun setFragmentLocation()
    fun setFragmentCalls()
    fun setFragmentSms()
    fun setFragmentRecords()
    fun setFragmentPhotos()
    fun setFragmentKeylog()
    fun setFragmentSocial()
    fun setFragmentSetting()
    fun setFragmentNotifyMessage()
    fun setFragmentAbout()

}