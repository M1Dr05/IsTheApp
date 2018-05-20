package com.github.midros.child.utils

import android.content.Context
import android.media.MediaMetadataRetriever
import android.provider.ContactsContract
import com.github.midros.child.utils.Consts.ADDRESS_AUDIO
import com.github.midros.child.utils.Consts.TAG
import com.pawegio.kandroid.e
import java.io.File
import java.lang.Long.parseLong

/**
 * Created by luis rafael on 27/03/18.
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

    @Throws(Exception::class)
    fun Context.getContactName(phoneNum: String?): String {
        if (phoneNum==null) throw Exception("Phone number can't be empty")

        var res = phoneNum.replace("[*+-]".toRegex(), "")
        val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER)

        val names = contentResolver.query(uri, projection, null, null, null)

        val indexName = names.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
        val indexNumber = names.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

        if (names.count > 0) {
            names.moveToFirst()
            do {
                val name = names.getString(indexName)
                val number = names.getString(indexNumber).replace("[*+-]".toRegex(), "")

                if (number.compareTo(res) == 0) {
                    res = name
                    break
                }
            } while (names.moveToNext())
        }

        names.close()
        return res
    }

    fun getDurationFile(fileName:String) : String{
        val metaRetriever = MediaMetadataRetriever()
        metaRetriever.setDataSource(fileName)
        val duration = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        val dur = parseLong(duration)
        val seconds = (dur % 60000 / 1000).toString()
        val minutes = (dur / 60000).toString()
        metaRetriever.release()
        return if (seconds.length == 1) "$minutes:0$seconds" else "$minutes:$seconds"
    }

    fun deleteFile(fileName: String?) {
        if (fileName == null)
            return
        try {
            val file = File(fileName)

            if (file.exists()) {
                file.delete()
            }
        } catch (e: Exception) {
            e(TAG,e.message.toString())
        }
    }
}