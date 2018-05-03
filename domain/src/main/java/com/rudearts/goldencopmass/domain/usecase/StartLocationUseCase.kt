package com.rudearts.goldencopmass.domain.usecase

import com.rudearts.goldencopmass.domain.sensor.LocationControllable
import io.reactivex.Observable
import javax.inject.Inject

class StartLocationUseCase @Inject constructor(
        private val locationControllable: LocationControllable) {

    fun startLocationUpdates():Observable<Float> = locationControllable.startUpdates()

}