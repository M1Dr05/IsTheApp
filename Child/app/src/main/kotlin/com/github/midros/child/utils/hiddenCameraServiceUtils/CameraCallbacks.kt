package com.github.midros.child.utils.hiddenCameraServiceUtils

import java.io.File

/**
 * Created by luis rafael on 20/03/18.
 */
interface CameraCallbacks {

    fun onImageCapture(imageFile: File)

    fun onCameraError(@CameraError.CameraErrorCodes errorCode: Int)
}
