package com.rudearts.goldencompass.di.component

import com.rudearts.goldencompass.di.module.AppModule
import com.rudearts.goldencompass.di.module.LocationModule
import com.rudearts.goldencompass.di.module.ViewModelModule
import com.rudearts.goldencompass.ui.location.LocationActivity
import com.rudearts.goldencompass.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (LocationModule::class), (ViewModelModule::class)])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(activity: LocationActivity)
}