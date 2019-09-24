package com.github.midros.istheapp.ui.adapters.basedapter

import com.google.firebase.database.DatabaseError

interface InterfaceAdapter {
    fun successResult(result: Boolean, filter:Boolean = false)
    fun failedResult(error: DatabaseError)
}