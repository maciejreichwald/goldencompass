package com.rudearts.goldencompass.ui.location

import android.app.Activity
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.rudearts.goldencompass.R
import com.rudearts.goldencompass.extentions.bind
import com.rudearts.goldencompass.extentions.getAppInjector
import com.rudearts.goldencompass.extentions.getViewModel
import com.rudearts.goldencompass.extentions.observe
import com.rudearts.goldencompass.ui.ToolbarActivity
import com.rudearts.goldencopmass.domain.model.BaseLocation
import javax.inject.Inject

class LocationActivity : ToolbarActivity(), OnMapReadyCallback {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    internal lateinit var viewModel:LocationViewModel

    internal val btnSave:View by bind(R.id.save_btn)
    internal var map:GoogleMap? = null

    override fun provideSubContentViewId() = R.layout.activity_location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    internal fun setup() {
        inject()
        setupTitle()
        setupMapFragment()
        setupBtn()
        setupViewModel()
    }

    private fun setupBtn() {
        btnSave.setOnClickListener { onSaveClick() }
    }

    private fun onSaveClick() {
        viewModel.save()
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun setupMapFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    internal fun setupTitle() {
        title = getString(R.string.choose_location)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupViewModel() {
        viewModel = getViewModel<LocationViewModel>(viewModelFactory).apply {
            observe(destination, ::onDestinationChange)
        }
        viewModel.loadDestination()
    }

    private fun onDestinationChange(baseLocation: BaseLocation?) {
        baseLocation?.let {
            val destination = LatLng(it.latitude, it.longitude)
            updateMapMarker(destination)
            updateCameraToPosition(destination)
        }
    }

    private fun updateCameraToPosition(destination: LatLng) {
        val camera = CameraPosition.Builder()
                .target(destination)
                .zoom(11f)
                .build()
        map?.animateCamera(CameraUpdateFactory.newCameraPosition(camera))
    }

    internal fun inject() {
        getAppInjector().inject(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap
        map?.uiSettings?.isMapToolbarEnabled = false
        map?.uiSettings?.isZoomControlsEnabled = false
        map?.setOnMapClickListener { latLng -> onMapClicked(latLng) }
    }

    private fun onMapClicked(latLng: LatLng?) {
        latLng?.let {
            updateMapMarker(it)
            viewModel.updateDestination(latLng)
        }
    }

    private fun updateMapMarker(latLng: LatLng) {
        map?.clear()
        map?.addMarker(MarkerOptions().apply {
            position(latLng)
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
