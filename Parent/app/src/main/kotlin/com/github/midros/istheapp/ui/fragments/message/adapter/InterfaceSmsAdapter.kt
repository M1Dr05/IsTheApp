package com.github.midros.istheapp.ui.fragments.message.adapter

import com.google.firebase.database.DatabaseError

/**
 * Created by luis rafael on 20/03/18.
 */
interface InterfaceSmsAdapter{
    fun successResult(boolean: Boolean)
    fun failedResult(error: DatabaseError)
    fun onLongClickSms(keySms:String)
}