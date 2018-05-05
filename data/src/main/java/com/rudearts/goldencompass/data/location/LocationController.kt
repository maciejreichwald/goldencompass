package com.rudearts.goldencompass.data.location

import android.annotation.SuppressLint
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import com.rudearts.goldencompass.data.util.BaseLocationMapper
import com.rudearts.goldencopmass.domain.sensor.LocationControllable
import com.rudearts.goldencopmass.domain.model.BaseLocation
import com.rudearts.goldencopmass.domain.sensor.LocationCache
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import javax.inject.Inject

class LocationController @Inject constructor(
        private val locationManager: LocationManager?,
        private val cache:LocationCache,
        private val mapper: BaseLocationMapper) : LocationControllable {

    var locationListener:LocationListener? = null

    @SuppressLint("MissingPermission")
    override fun startUpdates():Observable<Float> = Observable.create<Float> { subscriber ->
        stopLocationUpdates()

        locationListener = createLocationListener(subscriber)

        try {
            locationManager?.let {
                it.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
                it.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, locationListener)
            }
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }

    internal fun createLocationListener(subriber:ObservableEmitter<Float>) = object: BaseLocationListener() {
        override fun onLocationChanged(location: Location?) {
            location?.let {
                val destination = mapper.base2location(cache.loadDestination())
                val bearing = location.bearingTo(destination)
                subriber.onNext(bearing)
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun stopLocationUpdates() {
        locationListener?.let { listener ->
            locationManager?.removeUpdates(listener)
        }
    }

    @SuppressLint("MissingPermission")
    override fun getLastKnownLocation() = locationManager?.let {
        val location = it.getLastKnownLocation(it.getBestProvider(Criteria(), false))
        mapper.location2base(location)
    }

}