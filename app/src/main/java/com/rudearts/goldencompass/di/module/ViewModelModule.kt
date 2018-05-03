package com.rudearts.goldencompass.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.rudearts.goldencompass.di.ViewModelFactory
import com.rudearts.goldencompass.ui.location.LocationViewModel
import com.rudearts.goldencompass.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelFactory.ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelFactory.ViewModelKey(LocationViewModel::class)
    internal abstract fun binLocationViewModel(viewModel: LocationViewModel): ViewModel

}
