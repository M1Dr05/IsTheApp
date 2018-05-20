package com.github.midros.child.di.module

import com.github.midros.child.data.rxFirebase.DevelopFirebase
import com.github.midros.child.data.rxFirebase.InterfaceFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides

/**
 * Created by luis rafael on 13/03/18.
 */
@Module
class FirebaseModule {

    @Provides
    fun provideInterfaceFirebase(auth: FirebaseAuth, dataRef: DatabaseReference, stoRef: StorageReference): InterfaceFirebase = DevelopFirebase(auth, dataRef, stoRef)

    @Provides
    fun provideDatabaseReference(database: FirebaseDatabase): DatabaseReference = database.reference

    @Provides
    fun provideStorageReference(storage: FirebaseStorage): StorageReference = storage.reference

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance()

    @Provides
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

}