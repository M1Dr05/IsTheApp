package com.github.midros.istheapp.ui.adapters.callsadapter

import com.github.midros.istheapp.ui.adapters.basedapter.InterfaceAdapter
import java.io.File

/**
 * Created by luis rafael on 28/03/18.
 */
interface InterfaceCallsAdapter : InterfaceAdapter {
    fun onCheckPermissionAudioCalls(key:String,file: File, childName: String, fileName: String, holder: CallsViewHolder,position:Int)
    fun onClickDownloadAudioCall(file: File, childName: String, holder: CallsViewHolder)
    fun onLongClickDeleteFileCall(keyFileName: String, fileName: String, childName: String,position:Int)
}