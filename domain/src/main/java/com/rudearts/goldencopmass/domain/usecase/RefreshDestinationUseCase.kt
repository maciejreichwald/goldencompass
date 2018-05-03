package com.rudearts.goldencopmass.domain.usecase

import com.rudearts.goldencopmass.domain.model.BaseLocation
import com.rudearts.goldencopmass.domain.sensor.LocationCache
import javax.inject.Inject

class RefreshDestinationUseCase @Inject constructor(
        private val cache:LocationCache) {


    fun getDestination():BaseLocation? = cache.loadDestination()

}
