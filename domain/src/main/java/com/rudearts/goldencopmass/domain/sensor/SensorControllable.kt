package com.rudearts.goldencopmass.domain.sensor

import io.reactivex.Observable

interface SensorControllable {

    fun startUpdates(): Observable<Float>

    fun stopUpdates()

}