package com.rudearts.goldencopmass.domain.usecase

import com.rudearts.goldencopmass.domain.sensor.SensorControllable
import io.reactivex.Observable
import javax.inject.Inject

class StartCompassSensorUseCase @Inject constructor(
        private val sensorControllable: SensorControllable) {

    fun startSensorUpdates():Observable<Float> = sensorControllable.startUpdates()

}