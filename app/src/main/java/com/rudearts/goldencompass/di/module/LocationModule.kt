package com.rudearts.goldencompass.di.module

import android.content.Context
import android.hardware.SensorManager
import android.location.LocationManager
import com.rudearts.goldencompass.data.location.LocationController
import com.rudearts.goldencompass.data.location.LocationStorage
import com.rudearts.goldencompass.data.sensor.SensorCache
import com.rudearts.goldencompass.data.sensor.SensorController
import com.rudearts.goldencompass.data.util.BaseLocationMapper
import com.rudearts.goldencompass.data.util.SensorCalculator
import com.rudearts.goldencopmass.domain.sensor.LocationCache
import com.rudearts.goldencopmass.domain.sensor.LocationControllable
import com.rudearts.goldencopmass.domain.sensor.SensorControllable
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocationModule {

    @Provides
    fun providesLocationManager(context: Context) = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?

    @Singleton
    @Provides
    fun providesLocationControllable(
            locationManager: LocationManager?,
            cache: LocationCache,
            mapper:BaseLocationMapper) = LocationController(locationManager, cache, mapper) as LocationControllable

    @Singleton
    @Provides
    fun providesLocationCache() = LocationStorage() as LocationCache

    @Provides
    fun providesSensorManager(context: Context) = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager?

    @Provides
    fun providesSensorCache() = SensorCache()

    @Singleton
    @Provides
    fun providesSensorControllable(
            sensorManager: SensorManager?,
            calculator: SensorCalculator,
            cache:SensorCache) = SensorController(sensorManager, calculator, cache) as SensorControllable
}