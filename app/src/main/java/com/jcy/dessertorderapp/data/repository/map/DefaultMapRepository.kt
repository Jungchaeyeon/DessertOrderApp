package com.jcy.dessertorderapp.data.repository.map

import com.jcy.dessertorderapp.data.entity.LocationLatLngEntity
import com.jcy.dessertorderapp.data.network.MapApiService
import com.jcy.dessertorderapp.data.response.address.AddressInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultMapRepository(
    private val mapApiService: MapApiService,
    private val ioDispather: CoroutineDispatcher
): MapRepository {

    override suspend fun getReverseGeoInfomation(
        locationLatLngEntity: LocationLatLngEntity
    ): AddressInfo? = withContext(ioDispather){
        val response = mapApiService.getReverseGeoCode(
                lat = locationLatLngEntity.latitude,
                lon = locationLatLngEntity.longitude
        )
        if(response.isSuccessful){
            response.body()?.addressInfo
        }else{
            null
        }
    }
}