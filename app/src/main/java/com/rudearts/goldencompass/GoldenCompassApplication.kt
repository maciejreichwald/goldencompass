package com.rudearts.goldencompass

import android.app.Application
import com.rudearts.goldencompass.di.component.AppComponent
import com.rudearts.goldencompass.di.component.DaggerAppComponent
import com.rudearts.goldencompass.di.module.AppModule

class GoldenCompassApplication : Application() {

    lateinit var appComponent: AppComponent private set

    override fun onCreate() {
        super.onCreate()

        initInjector()
    }

    private fun initInjector() {
        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }
}