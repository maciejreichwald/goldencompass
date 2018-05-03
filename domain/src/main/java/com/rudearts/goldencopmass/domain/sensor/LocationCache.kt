package com.rudearts.goldencopmass.domain.sensor

import com.rudearts.goldencopmass.domain.model.BaseLocation

interface LocationCache {

    fun loadDestination():BaseLocation?
    fun saveDestination(destination:BaseLocation)
    fun clearDestination()

}