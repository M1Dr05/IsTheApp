package com.github.midros.istheapp.data.rxFirebase

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.StorageReference
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import java.io.File

/**
 * Created by luis rafael on 8/03/18.
 */
interface InterfaceFirebase {

    fun getUser(): FirebaseUser?

    fun signIn(email: String, password: String): Maybe<AuthResult>

    fun signUp(email: String, password: String): Maybe<AuthResult>

    fun signOut()

    fun valueEvent(child: String): Flowable<DataSnapshot>

    fun <T> valueEventModel(child: String, clazz: Class<T>): Flowable<T>

    fun getDatabaseReference(child: String): DatabaseReference

    fun getStorageReference(child: String): StorageReference

    fun getFile(child: String, file: File): Single<FileDownloadTask.TaskSnapshot>

}