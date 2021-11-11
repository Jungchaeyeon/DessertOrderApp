package com.jcy.dessertorderapp.data.repository.map

import com.jcy.dessertorderapp.data.entity.LocationLatLngEntity
import com.jcy.dessertorderapp.data.response.address.AddressInfo

interface MapRepository{
    suspend fun getReverseGeoInfomation(
        locationLatLngEntity: LocationLatLngEntity
    ): AddressInfo?
}