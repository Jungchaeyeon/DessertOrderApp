package com.jcy.dessertorderapp.screen.mylocation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jcy.dessertorderapp.R
import com.jcy.dessertorderapp.data.entity.LocationLatLngEntity
import com.jcy.dessertorderapp.data.entity.MapSearchInfoEntity
import com.jcy.dessertorderapp.data.repository.map.MapRepository
import com.jcy.dessertorderapp.data.repository.user.UserRepository
import com.jcy.dessertorderapp.screen.base.BaseViewModel
import com.jcy.dessertorderapp.screen.main.home.HomeState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyLocationViewModel(
    private val mapSearchInfoEntity: MapSearchInfoEntity,
    private val mapRepository: MapRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {
    val myLocationStateLiveData = MutableLiveData<MyLocationState>()

    override fun fetchData(): Job = viewModelScope.launch{
        myLocationStateLiveData.value = MyLocationState.Loading
        myLocationStateLiveData.value = MyLocationState.Success(
            mapSearchInfoEntity
        )
    }
    fun changeLocationInfo(
        locationLatLngEntity: LocationLatLngEntity
    ) = viewModelScope.launch {

        val addressInfo = mapRepository.getReverseGeoInfomation(locationLatLngEntity)
        addressInfo?.let { info ->
            myLocationStateLiveData.value = MyLocationState.Success(
                mapSearchInfoEntity = info.toSearchInfoEntity(locationLatLngEntity)
            )
        }?:kotlin.run {
            myLocationStateLiveData.value = MyLocationState.Error(
                R.string.can_not_load_address_info
            )
        }
    }
    fun confirmSelectLocation() = viewModelScope.launch {
        when(val data = myLocationStateLiveData.value){
            is MyLocationState.Success ->{
                userRepository.insertUserLocation(data.mapSearchInfoEntity.locationLatLngEntity)
                myLocationStateLiveData.value = MyLocationState.Confirm(
                    data.mapSearchInfoEntity
                )
            }
        }
    }
}