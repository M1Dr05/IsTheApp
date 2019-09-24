package com.github.midros.istheapp.services.notificationService

import android.graphics.Bitmap

/**
 * Created by luis rafael on 27/03/19.
 */
interface InterfaceNotificationListener {

    fun setRunService(run : Boolean)
    fun getNotificationExists(id:String,exec:() -> Unit)
    fun setDataMessageNotification(type:Int,text:String?,title:String?,nameImage:String?,image:Bitmap?)

}