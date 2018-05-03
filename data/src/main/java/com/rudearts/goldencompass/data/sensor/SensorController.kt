package com.rudearts.goldencompass.data.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.rudearts.goldencompass.data.util.SensorCalculator
import com.rudearts.goldencopmass.domain.sensor.SensorControllable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import javax.inject.Inject


class SensorController @Inject constructor(
        private val manager: SensorManager?,
        private val calculator:SensorCalculator,
        private val cache:SensorCache) : SensorControllable {

    internal val accelerometerSensor = manager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    internal val magneticSensor = manager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    internal var listener: SensorEventListener? = null

    override fun startUpdates():Observable<Float> = Observable.create<Float> { subscriber ->
        stopUpdates()

        listener = createSensorListener(subscriber)

        manager?.registerListener(listener, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME)
        manager?.registerListener(listener, magneticSensor, SensorManager.SENSOR_DELAY_GAME)
    }

    private fun createSensorListener(subscriber: ObservableEmitter<Float>) = object: BaseSensorEventListener() {
        override fun onSensorChanged(event: SensorEvent?) = synchronized(this) {
            val angle = calculator.calculateNorthAngle(event, cache)
            subscriber.onNext(angle)
        }
    }

    override fun stopUpdates() {
        listener?.let {
            manager?.unregisterListener(it)
        }
    }
}