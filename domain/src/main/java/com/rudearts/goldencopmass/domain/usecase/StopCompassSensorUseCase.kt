package com.rudearts.goldencopmass.domain.usecase

import com.rudearts.goldencopmass.domain.sensor.SensorControllable
import io.reactivex.Observable
import javax.inject.Inject

class StopCompassSensorUseCase @Inject constructor(
        private val sensorControllable: SensorControllable) {

    fun stopSensorUpdates() {
        sensorControllable.stopUpdates()
    }

}