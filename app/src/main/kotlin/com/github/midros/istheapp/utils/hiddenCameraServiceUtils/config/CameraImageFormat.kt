package com.github.midros.istheapp.utils.hiddenCameraServiceUtils.config

import androidx.annotation.IntDef

/**
 * Created by luis rafael on 20/03/18.
 */
class CameraImageFormat{

    init {
        throw RuntimeException("Cannot initialize CameraImageFormat.")
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(FORMAT_JPEG)
    annotation class SupportedImageFormat

    companion object {
        const val FORMAT_JPEG = 849
    }
}
