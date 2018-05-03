package com.rudearts.goldencompass.di.module

import android.content.Context
import com.rudearts.goldencompass.GoldenCompassApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val application: GoldenCompassApplication) {

    @Provides
    @Singleton
    fun provideApplication(): GoldenCompassApplication = application

    @Provides
    fun provideContext(): Context = application
}
