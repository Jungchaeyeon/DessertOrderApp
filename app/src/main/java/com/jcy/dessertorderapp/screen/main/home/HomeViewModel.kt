package com.jcy.dessertorderapp.screen.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jcy.dessertorderapp.R
import com.jcy.dessertorderapp.data.entity.LocationLatLngEntity
import com.jcy.dessertorderapp.data.entity.MapSearchInfoEntity
import com.jcy.dessertorderapp.data.repository.map.MapRepository
import com.jcy.dessertorderapp.screen.base.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel(
    private val mapRepository: MapRepository
): BaseViewModel() {

    companion object{
        const val MY_LOCATION_KEY = "MyLocation"
    }
    val homeStateLiveData = MutableLiveData<HomeState>(HomeState.Unititialized)

    fun loadReverseGeoInfomation(locationLatLngEntity: LocationLatLngEntity)
    = viewModelScope.launch{
        homeStateLiveData.value = HomeState.Loading
        val addressInfo = mapRepository.getReverseGeoInfomation(locationLatLngEntity)
        addressInfo?.let { info ->
            homeStateLiveData.value = HomeState.Success(
                mapSearchInfoEntity = info.toSearchInfoEntity(locationLatLngEntity)
            )
        }?:kotlin.run {
            homeStateLiveData.value = HomeState.Error(
                R.string.can_not_load_address_info
            )
        }
    }
    fun getMapSearchInfo(): MapSearchInfoEntity? {
        when(val data = homeStateLiveData.value){
            is HomeState.Success -> {
                return data.mapSearchInfoEntity
            }
        }
        return null
    }
}