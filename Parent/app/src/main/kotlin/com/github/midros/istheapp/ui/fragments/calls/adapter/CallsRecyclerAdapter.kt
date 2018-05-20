package com.github.midros.istheapp.ui.fragments.calls.adapter

import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.github.midros.istheapp.R
import com.github.midros.istheapp.data.model.Calls
import com.github.midros.istheapp.utils.ConstFun.firebaseOptions
import com.github.midros.istheapp.utils.Consts.ADDRESS_AUDIO
import com.github.midros.istheapp.utils.Consts.TAG
import com.github.midros.istheapp.utils.FileHelper.getFileName
import com.github.midros.istheapp.utils.FileHelper.getFilePath
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.jakewharton.rxbinding2.view.RxView
import com.pawegio.kandroid.e
import com.pawegio.kandroid.inflateLayout
import java.io.File

/**
 * Created by luis rafael on 28/03/18.
 */
class CallsRecyclerAdapter(query: Query) : FirebaseRecyclerAdapter<Calls, CallsViewHolder>(firebaseOptions(query)) {

    private lateinit var interfaceCallsAdapter: InterfaceCallsAdapter
    private var holder: CallsViewHolder? = null

    override fun onDataChanged() {
        if (snapshots.size == 0) interfaceCallsAdapter.successResult(false)
        else interfaceCallsAdapter.successResult(true)
    }

    override fun onError(error: DatabaseError) {
        interfaceCallsAdapter.failedResult(error)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallsViewHolder =
            CallsViewHolder(parent.context.inflateLayout(R.layout.item_calls, parent, false), parent.context)

    override fun onBindViewHolder(holder: CallsViewHolder, position: Int, model: Calls) {
        holder.bind(getItem(position))

        val key = getRef(position).key
        var fileName = holder.itemView.context.getFileName(model.phoneNumber, model.dateTime)
        val childName = fileName.replace("${holder.itemView.context.getFilePath()}/$ADDRESS_AUDIO/", "")
        fileName = fileName.replace(":", "")
        val file = File(fileName)
        if (file.exists()) holder.setDrawableItemClick(R.drawable.ic_play_arrow_24dp)
        else holder.setDrawableItemClick(R.drawable.ic_file_download_black_24dp)

        holder.itemClick.setOnClickListener {
            interfaceCallsAdapter.onCheckPermissionAudioCalls(file, childName, fileName, holder)
        }

        RxView.longClicks(holder.itemClick).subscribe({
            interfaceCallsAdapter.onLongClickDeleteFileCall(key, fileName, childName)
        }, { e(TAG, it.message.toString()) })

    }

    fun onClickListener(holder: CallsViewHolder, file: File, fileName: String, childName: String) {
        if (file.exists()) {
            if (holder.getPlaying()) holder.onPauseAudioCall()
            else {
                if (holder.getStopPlayer()) {
                    stopOldAudioCall()
                    this.holder = holder
                    holder.initializeMediaPlayer(fileName)
                }
                holder.onPlayAudioCall()
            }
        } else interfaceCallsAdapter.onClickDownloadAudioCall(file, childName, holder)
    }

    fun onRecyclerAdapterListener(interfaceCallsAdapter: InterfaceCallsAdapter) {
        this.interfaceCallsAdapter = interfaceCallsAdapter
    }

    fun stopOldAudioCall() {
        if (holder != null) holder!!.onStopAudioCall()
    }

}