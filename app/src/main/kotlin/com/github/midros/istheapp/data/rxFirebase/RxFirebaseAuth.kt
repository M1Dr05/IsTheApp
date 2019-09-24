package com.github.midros.istheapp.data.rxFirebase

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Maybe

/**
 * Created by luis rafael on 28/03/18.
 */
object RxFirebaseAuth {
    fun FirebaseAuth.rxSignInWithEmailAndPassword(email: String, password: String): Maybe<AuthResult> =
            Maybe.create { emitter -> RxTask.assignOnTask(emitter, signInWithEmailAndPassword(email, password)) }


    fun FirebaseAuth.rxCreateUserWithEmailAndPassword(email: String, password: String): Maybe<AuthResult> =
            Maybe.create { emitter -> RxTask.assignOnTask(emitter, createUserWithEmailAndPassword(email, password)) }


    fun FirebaseAuth.rxSignInWithCredential(credential: AuthCredential) : Maybe<AuthResult> =
            Maybe.create { emitter -> RxTask.assignOnTask(emitter,signInWithCredential(credential)) }

}