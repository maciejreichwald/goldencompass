package com.rudearts.goldencopmass.domain.usecase

import com.rudearts.goldencopmass.domain.sensor.LocationControllable
import javax.inject.Inject

class StopLocationUseCase @Inject constructor(
        private val locationControllable: LocationControllable) {

    fun stopLocationUpdates() {
        locationControllable.stopLocationUpdates()
    }

}