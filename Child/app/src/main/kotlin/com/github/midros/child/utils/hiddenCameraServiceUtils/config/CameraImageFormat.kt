package com.github.midros.child.utils.hiddenCameraServiceUtils.config

import android.support.annotation.IntDef

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
