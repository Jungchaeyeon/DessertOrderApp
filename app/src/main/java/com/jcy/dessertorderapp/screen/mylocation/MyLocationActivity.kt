package com.jcy.dessertorderapp.screen.mylocation

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.jcy.dessertorderapp.R
import com.jcy.dessertorderapp.data.entity.LocationLatLngEntity
import com.jcy.dessertorderapp.data.entity.MapSearchInfoEntity
import com.jcy.dessertorderapp.databinding.ActivityMainBinding
import com.jcy.dessertorderapp.databinding.ActivityMyLocationBinding
import com.jcy.dessertorderapp.screen.base.BaseActivity
import com.jcy.dessertorderapp.screen.main.home.HomeViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MyLocationActivity : BaseActivity<MyLocationViewModel, ActivityMyLocationBinding>(), OnMapReadyCallback {

    override val viewModel by viewModel<MyLocationViewModel> {
        parametersOf(
            intent.getParcelableExtra<MapSearchInfoEntity>(HomeViewModel.MY_LOCATION_KEY)
        )
    }

    override fun getViewBinding(): ActivityMyLocationBinding = ActivityMyLocationBinding.inflate(layoutInflater)

    private lateinit var map: GoogleMap

    private var isMapInitialized: Boolean = false

    private var  isChangeLocation: Boolean = false


    override fun onMapReady(map: GoogleMap) {
        this.map = map ?: return
        viewModel.fetchData()
    }

    override fun initViews() = with(binding){
        toolbar.setNavigationOnClickListener {
            finish()
        }
        confirmButton.setOnClickListener {
            viewModel.confirmSelectLocation()
        }
        setupGoogleMap()
    }
    private fun setupGoogleMap(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    override fun observeData() = viewModel.myLocationStateLiveData.observe(this){
        when(it){
            is MyLocationState.Loading ->{
                handleLoadingState()
            }
            is MyLocationState.Success ->{
                if(::map.isInitialized){
                    handleSuccessState(it)
                }
            }
            is MyLocationState.Confirm ->{
                setResult(Activity.RESULT_OK, Intent().apply{
                    putExtra(HomeViewModel.MY_LOCATION_KEY, it.mapSearchInfoEntity)
                })
                finish()
            }
            is MyLocationState.Error ->{
                Toast.makeText(this, it.messageId, Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }
    private fun handleLoadingState() = with(binding){
        locationLoading.isVisible = true
        locationTitle.text = getString(R.string.loading)
    }
    private fun handleSuccessState(state: MyLocationState.Success) = with(binding){
        val mapSearchInfo = state.mapSearchInfoEntity
        locationLoading.isGone = true
        locationTitle.text = mapSearchInfo.fullAddress
        if(isMapInitialized.not()){
            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        mapSearchInfo.locationLatLngEntity.latitude,
                        mapSearchInfo.locationLatLngEntity.longitude,
                    ),
                    CAMERA_ZOOM_LEVEL
                )
            )
            map.setOnCameraIdleListener {
                if(isChangeLocation.not()){//false면
                    isChangeLocation = true
                    Handler(Looper.getMainLooper()).postDelayed({
                        val cameraLatLng = map.cameraPosition.target
                        viewModel.changeLocationInfo(
                            LocationLatLngEntity(
                                cameraLatLng.latitude,
                                cameraLatLng.longitude
                            )
                        )
                        isChangeLocation = false
                    }, 1000)
                }
            }
            isMapInitialized = true
        }
    }

    companion object{
        const val CAMERA_ZOOM_LEVEL = 17f

        fun newIntent(context : Context, mapSearchInfoEntity : MapSearchInfoEntity)
                = Intent(context, MyLocationActivity::class.java).apply {
            putExtra(HomeViewModel.MY_LOCATION_KEY, mapSearchInfoEntity)
        }
    }



}