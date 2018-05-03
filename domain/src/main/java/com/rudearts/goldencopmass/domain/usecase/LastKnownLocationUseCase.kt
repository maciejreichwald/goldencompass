package com.rudearts.goldencopmass.domain.usecase

import com.rudearts.goldencopmass.domain.sensor.LocationControllable
import javax.inject.Inject

class LastKnownLocationUseCase @Inject constructor(
        private val locationControllable: LocationControllable) {

    fun getLocation() = locationControllable.getLastKnownLocation()

}