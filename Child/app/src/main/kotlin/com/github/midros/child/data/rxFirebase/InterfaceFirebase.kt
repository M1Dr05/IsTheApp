package com.github.midros.child.data.rxFirebase

import android.net.Uri
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Created by luis rafael on 13/03/18.
 */
interface InterfaceFirebase {

    fun getUser(): FirebaseUser?

    fun signOut()

    fun signIn(email: String, password: String): Maybe<AuthResult>

    fun valueEvent(child: String): Flowable<DataSnapshot>

    fun <T> valueEventModel(child: String, clazz: Class<T>): Flowable<T>

    fun getDatabaseReference(child: String): DatabaseReference

    fun getStorageReference(child: String): StorageReference

    fun putFile(child: String, uri: Uri): Single<UploadTask.TaskSnapshot>

}