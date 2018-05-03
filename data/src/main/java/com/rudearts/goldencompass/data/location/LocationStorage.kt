package com.rudearts.goldencompass.data.location

import com.rudearts.goldencopmass.domain.sensor.LocationCache
import com.rudearts.goldencopmass.domain.model.BaseLocation

class LocationStorage : LocationCache {

    internal var destination:BaseLocation? = null

    override fun loadDestination() = destination

    override fun saveDestination(destination: BaseLocation) {
        this.destination = destination
    }

    override fun clearDestination() {
        destination = null
    }
}