package com.github.midros.child.data.rxFirebase

import android.net.Uri
import com.github.midros.child.utils.Consts.USER
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

import com.github.midros.child.data.rxFirebase.RxFirebaseAuth.rxSignInWithEmailAndPassword
import com.github.midros.child.data.rxFirebase.RxFirebaseDatabase.rxObserveValueEvent
import com.github.midros.child.data.rxFirebase.RxFirebaseStorage.rxPutFile

/**
 * Created by luis rafael on 13/03/18.
 */
class DevelopFirebase @Inject constructor(private val auth: FirebaseAuth,
                                          private val dataRef: DatabaseReference,
                                          private val stoRef: StorageReference) : InterfaceFirebase {

    override fun getUser(): FirebaseUser? = auth.currentUser

    override fun signIn(email: String, password: String): Maybe<AuthResult> = auth.rxSignInWithEmailAndPassword(email, password)

    override fun signOut() = auth.signOut()

    override fun getDatabaseReference(child: String): DatabaseReference = dataRef.child(USER).child(getUser()!!.uid).child(child)

    override fun valueEvent(child: String): Flowable<DataSnapshot> = getDatabaseReference(child).rxObserveValueEvent()

    override fun <T> valueEventModel(child: String, clazz: Class<T>): Flowable<T> = getDatabaseReference(child).rxObserveValueEvent().map { it.getValue(clazz) }

    override fun getStorageReference(child: String): StorageReference = stoRef.child(USER).child(getUser()!!.uid).child(child)

    override fun putFile(child: String, uri: Uri): Single<UploadTask.TaskSnapshot> = getStorageReference(child).rxPutFile(uri)

}