package com.jcy.dessertorderapp.data.repository.user

import com.jcy.dessertorderapp.data.entity.LocationLatLngEntity

interface UserRepository {
    suspend fun getUserLocation(): LocationLatLngEntity?

    suspend fun insertUserLocation(locationlatLngEntity: LocationLatLngEntity)
}