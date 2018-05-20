package com.github.midros.child.utils.hiddenCameraServiceUtils.config

import android.support.annotation.IntDef

/**
 * Created by luis rafael on 20/03/18.
 */
class CameraResolution{

    init {
        throw RuntimeException("Cannot initiate CameraResolution.")
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(MEDIUM_RESOLUTION, SLOW_RESOLUTION)
    annotation class SupportedResolution

    companion object {
        const val MEDIUM_RESOLUTION = 2006
        const val SLOW_RESOLUTION = 7895
    }
}
