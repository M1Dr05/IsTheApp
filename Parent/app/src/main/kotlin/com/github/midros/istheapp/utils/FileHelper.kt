package com.github.midros.istheapp.utils

import android.content.Context
import com.github.midros.istheapp.R
import com.github.midros.istheapp.utils.Consts.ADDRESS_AUDIO
import com.pawegio.kandroid.longToast
import java.io.File


/**
 * Created by luis rafael on 20/03/18.
 */
object FileHelper{
    fun Context.getFilePath(): String =
            if (externalCacheDir == null) cacheDir.absolutePath
            else externalCacheDir.absolutePath

    @Throws(Exception::class)
    fun Context.getFileName(number: String?,dateTime:String?): String {
        val file: File?
        var phoneNumber: String? = number ?: throw Exception("Phone number can't be empty")

        try {
            file = File(getFilePath(), ADDRESS_AUDIO)

            if (!file.exists()) {
                file.mkdirs()
            }

            // Clean characters in file name
            phoneNumber = phoneNumber!!.replace("[*+-]".toRegex(), "")
            if (phoneNumber.length > 10) {
                phoneNumber.substring(phoneNumber.length - 10, phoneNumber.length)
            }
        } catch (e: Exception) {
            throw Exception(e)
        }

        return file.absolutePath + "/" + dateTime + "," + phoneNumber + ".mp3"
    }

    fun Context.deleteFileName(fileName: String?) {
        if (fileName == null)
            return
        try {
            val file = File(fileName)

            if (file.exists()) {
                file.delete()
            }
        } catch (e: Exception) {
            longToast("${getString(R.string.failed_delete_file)} ${e.message.toString()}")
        }
    }

    fun Context.deleteAllFile() {
        try {
            val file = File(getFilePath(), ADDRESS_AUDIO)
            if (file.isDirectory) {
                for (child in file.listFiles()) child.delete()
            } else {
                file.delete()
            }
        }catch (e:Exception){
            longToast("${getString(R.string.failed_delete_files)} ${e.message.toString()}")
        }
    }
}