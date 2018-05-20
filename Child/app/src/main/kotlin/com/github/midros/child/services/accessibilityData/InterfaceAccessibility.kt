package com.github.midros.child.services.accessibilityData

import android.location.Location

/**
 * Created by luis rafael on 17/03/18.
 */
interface InterfaceAccessibility {

    fun clearDisposable()

    fun setDataKey(data: String)

    fun setDataLocation(location: Location)

    fun getShowOrHideApp()

    fun getCapturePicture()

    fun setRunServiceData(run: Boolean)

    fun getSocialStatus()

    fun enablePermissionLocation(location: Boolean)

    fun enableGps(gps: Boolean)
}