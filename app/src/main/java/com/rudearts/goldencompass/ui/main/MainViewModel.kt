package com.rudearts.goldencompass.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rudearts.goldencopmass.domain.model.BaseLocation
import com.rudearts.goldencopmass.domain.usecase.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MainViewModel @Inject constructor(
        private val startCompassSensorUseCase: StartCompassSensorUseCase,
        private val stopCompassSensorUseCase: StopCompassSensorUseCase,
        private val startLocationUseCase: StartLocationUseCase,
        private val stopLocationUseCase: StopLocationUseCase,
        private val refreshDestinationUseCase: RefreshDestinationUseCase,
        private val getLastKnownLocationUseCase: LastKnownLocationUseCase) : ViewModel() {


    internal val northAngle = MutableLiveData<Float>()
    internal val destinationAngle = MutableLiveData<Float>()
    internal val locationUpdateState = MutableLiveData<Boolean>()
    internal val destination = MutableLiveData<BaseLocation>()
    private var localDestinationAngle = 0f

    private var destinationDisposable:Disposable? = null
    private val compositeDisposable = CompositeDisposable()

    fun startCompassSensor() {
        compositeDisposable.add(startCompassSensorUseCase.startSensorUpdates()
                .subscribe({
                    northAngle.postValue(it)
                    postDestinationAngle()
                }, { }))
    }

    fun startSeekingDestination() {
        if (getInverseLocationState()) return

        val disposable = startLocationUseCase.startLocationUpdates()
                .subscribe({ angle -> postDestinationAngle(angle) })

        destinationDisposable = disposable
        compositeDisposable.add(disposable)
    }

    fun onLocationClick() {
        locationUpdateState.postValue(getInverseLocationState())
        startSeekingDestination()
    }

    fun refreshDestination() {
        val location = refreshDestinationUseCase.getDestination()
        when (location) {
            null -> destination.postValue(getLastKnownLocationUseCase.getLocation())
            else -> destination.postValue(location)
        }
    }

    private fun getInverseLocationState() = when(locationUpdateState.value) {
        true -> false
        else -> true
    }

    private fun postDestinationAngle() {
        postDestinationAngle(localDestinationAngle)
    }

    private fun postDestinationAngle(angle: Float) {
        localDestinationAngle = angle
        northAngle.value?.let {
            destinationAngle.postValue(angle + it)
        }
    }

    fun stopCompassSensor() {
        stopCompassSensorUseCase.stopSensorUpdates()
    }

    fun stopSeekingDestination() {
        destinationDisposable?.dispose()
        stopLocationUseCase.stopLocationUpdates()
    }

    override fun onCleared() {
        stopCompassSensorUseCase.stopSensorUpdates()
        stopLocationUseCase.stopLocationUpdates()
        compositeDisposable.dispose()
        super.onCleared()
    }
}