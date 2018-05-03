package com.rudearts.goldencopmass.domain.sensor

import com.rudearts.goldencopmass.domain.model.BaseLocation
import io.reactivex.Observable

interface LocationControllable {

    fun startUpdates(): Observable<Float>

    fun stopLocationUpdates()

    fun getLastKnownLocation():BaseLocation?

}