package com.rudearts.goldencompass.data.sensor

class SensorCache {

    companion object {
        const val EVENT_VALUES_ARRAY_LENGTH = 3
        const val MATRIX_SIZE = 9
    }

    var gravity = FloatArray(EVENT_VALUES_ARRAY_LENGTH)
    var geomagnetic = FloatArray(EVENT_VALUES_ARRAY_LENGTH)

}