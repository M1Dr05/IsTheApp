package com.github.midros.istheapp.ui.fragments.maps

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.github.midros.istheapp.R
import com.github.midros.istheapp.data.model.Location
import com.github.midros.istheapp.ui.activities.base.BaseFragment
import com.github.midros.istheapp.utils.Consts.TAG
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.pawegio.kandroid.e
import kotterknife.bindView
import javax.inject.Inject

/**
 * Created by luis rafael on 9/03/18.
 */
class MapsFragment : BaseFragment(), OnMapReadyCallback, InterfaceViewMaps {

    private val imgState: ImageView by bindView(R.id.img_gps_state)
    private val imgPermission: ImageView by bindView(R.id.img_permission_state)

    private lateinit var maps: GoogleMap

    @Inject
    lateinit var mapsFragment: SupportMapFragment

    @Inject
    lateinit var interactor: InterfaceInteractorMaps<InterfaceViewMaps>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_maps, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (getComponent() != null) {
            getComponent()!!.inject(this)
            interactor.onAttach(this)
            initializeMaps()
        }
    }

    override fun onStart() {
        super.onStart()
        interactor.valueEventEnableGps()
        interactor.valueEventEnablePermission()
    }

    private fun initializeMaps() {
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.maps_fragment, mapsFragment).commit()
        mapsFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        this.maps = map
        maps.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(5.3726154, -73.9312649), 5f))
        interactor.valueEventLocation()
    }

    override fun setLocation(location: Location) {
        maps.clear()
        val latitude = location.latitude!!
        val longitude = location.longitude!!
        val address = location.address
        val dateTime = location.dateTime
        maps.addMarker(MarkerOptions().position(LatLng(latitude, longitude)).title(address).snippet(dateTime)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location)))
        maps.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), 16f))
    }

    override fun setValueState(dataSnapshot: DataSnapshot) {
        try {
            if (dataSnapshot.exists()) {
                if (dataSnapshot.value as Boolean) imgState.background = ContextCompat.getDrawable(activity!!, R.drawable.ic_status_key_enable_24dp)
                else
                    imgState.background = ContextCompat.getDrawable(activity!!, R.drawable.ic_status_key_disable_24dp)
            } else {
                imgState.background = ContextCompat.getDrawable(activity!!, R.drawable.ic_status_key_disable_24dp)
            }
        } catch (t: Throwable) {
            e(TAG, t.message.toString())
        }
    }

    override fun setValuePermission(dataSnapshot: DataSnapshot) {
        try {
            if (dataSnapshot.exists()) {
                if (dataSnapshot.value as Boolean) imgPermission.background = ContextCompat.getDrawable(activity!!, R.drawable.ic_status_key_enable_24dp)
                else
                    imgPermission.background = ContextCompat.getDrawable(activity!!, R.drawable.ic_status_key_disable_24dp)
            } else {
                imgPermission.background = ContextCompat.getDrawable(activity!!, R.drawable.ic_status_key_disable_24dp)
            }
        } catch (t: Throwable) {
            e(TAG, t.message.toString())
        }
    }


    override fun onDetach() {
        clearDisposable()
        interactor.onDetach()
        super.onDetach()
    }


}