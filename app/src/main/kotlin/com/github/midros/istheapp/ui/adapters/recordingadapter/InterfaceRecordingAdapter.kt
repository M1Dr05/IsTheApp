package com.github.midros.istheapp.ui.adapters.recordingadapter

import com.github.midros.istheapp.ui.adapters.basedapter.InterfaceAdapter
import java.io.File

/**
 * Created by luis rafael on 28/03/18.
 */
interface InterfaceRecordingAdapter : InterfaceAdapter{
    fun onCheckPermissionAudioRecord(key:String?,file: File, childName: String, fileName: String, holder: RecordingViewHolder,position:Int)
    fun onClickDownloadAudioRecord(file: File, childName: String, holder: RecordingViewHolder)
    fun onLongClickDeleteFileRecord(keyFileName: String?, fileName: String, childName: String,position:Int)

}