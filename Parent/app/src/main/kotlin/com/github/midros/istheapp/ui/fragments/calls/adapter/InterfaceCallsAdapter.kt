package com.github.midros.istheapp.ui.fragments.calls.adapter

import com.google.firebase.database.DatabaseError
import java.io.File

/**
 * Created by luis rafael on 28/03/18.
 */
interface InterfaceCallsAdapter {

    fun successResult(boolean: Boolean)
    fun failedResult(error: DatabaseError)

    fun onCheckPermissionAudioCalls(file: File, childName: String, fileName: String, holder: CallsViewHolder)
    fun onClickDownloadAudioCall(file: File, childName: String, holder: CallsViewHolder)
    fun onLongClickDeleteFileCall(keyFileName: String, fileName: String, childName: String)
}