package com.rudearts.goldencopmass.domain.usecase

import com.rudearts.goldencopmass.domain.model.BaseLocation
import com.rudearts.goldencopmass.domain.model.BaseViewLocation
import com.rudearts.goldencopmass.domain.sensor.LocationCache
import com.rudearts.goldencopmass.domain.util.BaseViewLocationMapper
import javax.inject.Inject

class SaveDestinationUseCase @Inject constructor(
        private val cache:LocationCache) {

    fun save(destination:BaseLocation) {
        cache.saveDestination(destination)
    }

}