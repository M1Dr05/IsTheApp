package com.github.midros.istheapp.ui.fragments.keylog.adapter

import com.google.firebase.database.DatabaseError

/**
 * Created by luis rafael on 20/03/18.
 */
interface InterfaceKeysAdapter {

    fun successResult(boolean: Boolean)
    fun failedResult(error: DatabaseError)
}