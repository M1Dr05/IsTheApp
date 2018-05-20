package com.github.midros.istheapp.ui.fragments.photo.adapter

import com.google.firebase.database.DatabaseError

/**
 * Created by luis rafael on 20/03/18.
 */
interface InterfacePhotoAdapter {

    fun successResult(boolean: Boolean)
    fun failedResult(error: DatabaseError)

    fun onLongClickDeleteFilePhoto(keyFileName: String, childName: String)

}