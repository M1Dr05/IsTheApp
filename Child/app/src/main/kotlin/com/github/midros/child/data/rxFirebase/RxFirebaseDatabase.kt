package com.github.midros.child.data.rxFirebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

/**
 * Created by luis rafael on 13/03/18.
 */
object RxFirebaseDatabase {

    fun Query.rxObserveValueEvent(): Flowable<DataSnapshot> {
        return Flowable.create({ emitter ->
            val valueEventListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) = emitter.onNext(dataSnapshot)

                override fun onCancelled(error: DatabaseError) {
                    if (!emitter.isCancelled) emitter.onError(Throwable(error.message.toString()))
                }
            }
            emitter.setCancellable { removeEventListener(valueEventListener) }
            addValueEventListener(valueEventListener)
        }, BackpressureStrategy.DROP)
    }

}