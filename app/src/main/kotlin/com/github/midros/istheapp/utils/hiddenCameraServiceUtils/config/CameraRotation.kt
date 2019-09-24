package com.github.midros.istheapp.utils.hiddenCameraServiceUtils.config

import androidx.annotation.IntDef

/**
 * Created by luis rafael on 20/03/18.
 */
class CameraRotation {

    init {
        throw RuntimeException("Cannot initialize this class.")
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(ROTATION_0, ROTATION_90, ROTATION_180, ROTATION_270)
    annotation class SupportedRotation

    companion object {
        const val ROTATION_90 = 90
        const val ROTATION_180 = 180
        const val ROTATION_270 = 270
        const val ROTATION_0 = 0
    }
}
