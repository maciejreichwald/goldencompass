package com.rudearts.goldencompass.ui.location

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.rudearts.goldencopmass.domain.model.BaseLocation
import com.rudearts.goldencopmass.domain.model.BaseViewLocation
import com.rudearts.goldencopmass.domain.usecase.LastKnownLocationUseCase
import com.rudearts.goldencopmass.domain.usecase.RefreshDestinationUseCase
import com.rudearts.goldencopmass.domain.usecase.SaveDestinationUseCase
import javax.inject.Inject

class LocationViewModel @Inject constructor(
        private val refreshDestinationUseCase: RefreshDestinationUseCase,
        private val saveDestinationUseCase:SaveDestinationUseCase,
        private val getLastKnownLocationUseCase: LastKnownLocationUseCase) : ViewModel() {

    internal val destination = MutableLiveData<BaseLocation>()

    fun loadDestination() {
        destination.value?.let {
            return
        }

        val location = refreshDestinationUseCase.getDestination()
        when (location) {
            null -> destination.postValue(getLastKnownLocationUseCase.getLocation())
            else -> destination.postValue(location)
        }
    }

    fun save() {
        destination.value?.let {
            saveDestinationUseCase.save(it)
        }
    }

    fun updateDestination(latLng: LatLng) {
        destination.value = BaseLocation(latLng.latitude, latLng.longitude)
    }

}