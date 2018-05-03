package com.rudearts.goldencompass.data.util

import android.location.Location
import com.rudearts.goldencopmass.domain.model.BaseLocation
import javax.inject.Inject

class BaseLocationMapper @Inject constructor() {

    fun base2location(baseLocation: BaseLocation?) = Location("").apply {
        latitude = baseLocation?.latitude ?: 0.0
        longitude = baseLocation?.longitude ?: 0.0
    }

    fun location2base(location: Location?) = location?.let {
        BaseLocation(it.latitude, it.longitude)
    }

}