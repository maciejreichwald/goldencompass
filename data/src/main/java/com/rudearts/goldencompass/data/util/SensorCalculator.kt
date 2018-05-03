package com.rudearts.goldencompass.data.util

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import com.rudearts.goldencompass.data.sensor.SensorStorage
import javax.inject.Inject

/**
 * Notwithstanding logic in data layer, if I move this to domain it will make it platform dependent.
 * On the other hand, if I divide the calculations between domain and data layers, it will be unreadable.
 * It's the lesser evil here.
 * Actually, if application gets bigger, I tend to move all device-specific parts to "industrial" level,
 * which handles such hybrids of data with "logic" layers.
 */
class SensorCalculator @Inject constructor() {

    companion object {
        private const val NORMALIZE_ALPHA = 0.97f
        private const val FIRST_PARAM_INDEX = 0
        const val DEFAULT_VALUE = 0f
    }

    fun calculateNorthAngle(event:SensorEvent?, cache: SensorStorage):Float {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            cache.gravity = normalizeNoise(cache.gravity, event.values)
        }

        if (event?.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD) {
            cache.geomagnetic = normalizeNoise(cache.geomagnetic, event.values)
        }

        val valuesR = FloatArray(SensorStorage.MATRIX_SIZE)

        val success = SensorManager.getRotationMatrix(valuesR, null, cache.gravity, cache.geomagnetic)
        if (!success)  return DEFAULT_VALUE

        SensorManager.remapCoordinateSystem(valuesR, SensorManager.AXIS_X, SensorManager.AXIS_Z, valuesR)
        SensorManager.remapCoordinateSystem(valuesR, SensorManager.AXIS_X, SensorManager.AXIS_Y, valuesR)

        val orientation = SensorManager.getOrientation(valuesR, FloatArray(SensorStorage.EVENT_VALUES_ARRAY_LENGTH))
        val azimuth = orientation[FIRST_PARAM_INDEX]
        val angle = Math.toDegrees(azimuth.toDouble()).toFloat()
        return (angle + 360f) % 360
    }

    internal fun normalizeNoise(values: FloatArray, eventData: FloatArray) = values.mapIndexed { index, item ->
        item * NORMALIZE_ALPHA + (1 - NORMALIZE_ALPHA) * eventData[index]
    }.toFloatArray()

}