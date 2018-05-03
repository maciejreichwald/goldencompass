package com.rudearts.goldencopmass.domain.util

import com.rudearts.goldencopmass.domain.model.BaseLocation
import com.rudearts.goldencopmass.domain.model.BaseViewLocation
import javax.inject.Inject

class BaseViewLocationMapper @Inject constructor() {

    companion object {
        private const val DEFAULT_VALUE = 0.0
    }

    internal fun view2baseDestination(viewDestionation: BaseViewLocation) = with(viewDestionation) {
        BaseLocation(string2double(latitude), string2double(longitude))
    }

    internal fun string2double(value: String): Double {
        try {
            return value.toDouble()
        } catch (ex:NumberFormatException) {
            ex.printStackTrace()
        }
        return DEFAULT_VALUE
    }

}