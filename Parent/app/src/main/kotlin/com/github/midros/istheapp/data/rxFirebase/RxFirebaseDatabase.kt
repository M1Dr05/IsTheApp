package com.github.midros.istheapp.data.rxFirebase

import com.github.midros.istheapp.utils.Consts.TAG
import com.google.firebase.database.*
import com.pawegio.kandroid.e
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

/**
 * Created by luis rafael on 28/03/18.
 */
object RxFirebaseDatabase {

    fun Query.rxObserveValueEvent(): Flowable<DataSnapshot> {
        return Flowable.create({ emitter ->
            val valueEventListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) = emitter.onNext(dataSnapshot)

                override fun onCancelled(error: DatabaseError) {
                    try { if (!emitter.isCancelled) emitter.onError(Throwable(error.message.toString())) }
                    catch (t: Throwable) { e(TAG, t.message.toString()) }
                }
            }
            emitter.setCancellable { removeEventListener(valueEventListener) }
            addValueEventListener(valueEventListener)
        }, BackpressureStrategy.DROP)
    }

}