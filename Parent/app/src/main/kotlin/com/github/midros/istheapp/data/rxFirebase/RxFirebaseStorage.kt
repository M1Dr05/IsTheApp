package com.github.midros.istheapp.data.rxFirebase

import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.StorageReference
import io.reactivex.Single
import java.io.File

/**
 * Created by luis rafael on 28/03/18.
 */
object RxFirebaseStorage {

    fun StorageReference.rxGetFile(destinationFile: File): Single<FileDownloadTask.TaskSnapshot> {
        return Single.create { emitter ->
            val taskSnapshotStorageTask = getFile(destinationFile)
                    .addOnSuccessListener { taskSnapshot -> emitter.onSuccess(taskSnapshot) }
                    .addOnFailureListener { error -> if (!emitter.isDisposed) { emitter.onError(error) } }
            emitter.setCancellable { taskSnapshotStorageTask.cancel() }
        }
    }

}