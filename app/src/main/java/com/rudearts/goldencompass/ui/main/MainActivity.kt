package com.rudearts.goldencompass.ui.main

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.rudearts.goldencompass.R
import com.rudearts.goldencompass.extentions.*
import com.rudearts.goldencompass.ui.ToolbarActivity
import com.rudearts.goldencompass.ui.location.LocationActivity
import com.rudearts.goldencopmass.domain.model.BaseLocation
import javax.inject.Inject


/**
 * Yes, it is open, you can see in MainActivityTest bottom comment why
 */
open class MainActivity : ToolbarActivity() {

    companion object {
        private const val LOCATION_ACTIVITY_REQUEST_CODE = 1231
        private const val LOCATION_CLICK_PERMISSION_REQUEST_CODE = 2323
        private const val LOCATION_VIEW_PERMISSION_REQUEST_CODE = 2324
        private const val DESTINATION_PERMISSION_REQUEST_CODE = 2325
    }

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    internal lateinit var viewModel:MainViewModel

    internal val imgCompass:ImageView by bind(R.id.compass)
    internal val imgArrow:ImageView by bind(R.id.arrow)
    internal val lblLatitude:TextView by bind(R.id.latitude_lbl)
    internal val lblLongitude:TextView by bind(R.id.longitude_lbl)
    internal val btnUpdate:View by bind(R.id.update_btn)
    internal val btnLocation:View by bind(R.id.location_btn)
    internal val lblLocation:TextView by bind(R.id.location_lbl)

    override fun provideSubContentViewId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    internal fun setup() {
        inject()
        setupTitle()
        setupViewModel()
        setupBtns()

        changeLocationViews(false)
    }

    internal fun setupBtns() {
        btnLocation.setOnClickListener { onLocationClick() }
        btnUpdate.setOnClickListener { openLocationView() }
    }

    internal fun onLocationClick() {
        if (checkAndRequestPermissionIfNeeded(LOCATION_CLICK_PERMISSION_REQUEST_CODE)) {
            return
        }

        viewModel.onLocationClick()
    }

    internal fun checkAndRequestPermissionIfNeeded(requestCode:Int): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), requestCode)
                return true
            }
        }
        return false
    }

    internal fun openLocationView() {
        if (checkAndRequestPermissionIfNeeded(LOCATION_VIEW_PERMISSION_REQUEST_CODE)) {
            return
        }

        Intent(this, LocationActivity::class.java).apply {
            startActivityForResult(this, LOCATION_ACTIVITY_REQUEST_CODE)
        }
    }

    internal fun changeLocationViews(locationState:Boolean) {
        lblLocation.setText(getTextByLocationState(locationState))
        imgArrow.visible = locationState
        lblLatitude.visibleOrNot = locationState
        lblLongitude.visibleOrNot = locationState
    }

    internal fun getTextByLocationState(locationState: Boolean) = when(locationState) {
        true -> R.string.stop
        else -> R.string.start
    }

    internal fun setupViewModel() {
        viewModel = getViewModel<MainViewModel>(viewModelFactory).apply {
            observe(northAngle, ::onNorthAngleChange)
            observe(destinationAngle, ::onDestinationAngleChange)
            observe(locationUpdateState, ::onLocationStateChanged)
            observe(destination, ::onDestinationChanged)
        }

        refreshDestination()
    }

    internal fun refreshDestination() {
        if (checkAndRequestPermissionIfNeeded(DESTINATION_PERMISSION_REQUEST_CODE)) {
            return
        }

        viewModel.refreshDestination()
    }

    internal fun onDestinationChanged(destination: BaseLocation?) {
        when (destination) {
            null -> refreshDestinationCoordinates(BaseLocation(0.0,0.0))
            else -> refreshDestinationCoordinates(destination)
        }
    }

    internal fun refreshDestinationCoordinates(destination:BaseLocation) {
        lblLatitude.text = resources.getString(R.string.latitude_value, destination.latitude)
        lblLongitude.text = resources.getString(R.string.longitude_value, destination.longitude)
    }

    internal fun onLocationStateChanged(state: Boolean?) = when(state) {
        null -> changeLocationViews(false)
        else -> changeLocationViews(state)
    }

    internal fun onDestinationAngleChange(angle: Float?) {
        angle?.let {
            imgArrow.rotation = angle
        }
    }

    internal fun onNorthAngleChange(angle: Float?) {
        angle?.let {
            imgCompass.rotation = angle
        }
    }

    internal fun inject() {
        getAppInjector().inject(this)
    }

    internal fun setupTitle() {
        title = getString(R.string.app_name)
    }

    override fun onResume() {
        super.onResume()

        viewModel.startCompassSensor()
        viewModel.startSeekingDestination()
    }

    override fun onPause() {
        super.onPause()

        viewModel.stopCompassSensor()
        viewModel.stopSeekingDestination()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        handleLocationRequests(requestCode, resultCode)
    }

    internal fun handleLocationRequests(requestCode: Int, resultCode: Int) {
        if (resultCode != Activity.RESULT_OK) return

        when(requestCode) {
            LOCATION_ACTIVITY_REQUEST_CODE -> viewModel.refreshDestination()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        handleLocationPermissions(requestCode, grantResults)
    }

    internal fun handleLocationPermissions(requestCode: Int, grantResults: IntArray) {
        if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) return

        when(requestCode) {
            DESTINATION_PERMISSION_REQUEST_CODE -> refreshDestination()
            LOCATION_CLICK_PERMISSION_REQUEST_CODE -> onLocationClickAfterPermission()
            LOCATION_VIEW_PERMISSION_REQUEST_CODE -> openLocationView()
        }
    }

    internal fun onLocationClickAfterPermission() {
        viewModel.refreshDestination()
        onLocationClick()
    }
}
