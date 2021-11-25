package com.jcy.dessertorderapp.data.repository.user

import com.jcy.dessertorderapp.data.entity.LocationLatLngEntity
import com.jcy.dessertorderapp.data.entity.RestaurantEntity

interface UserRepository {
    suspend fun getUserLocation(): LocationLatLngEntity?

    suspend fun insertUserLocation(locationlatLngEntity: LocationLatLngEntity)

    suspend fun getUserLikedRestaurant(restaurantTitle: String): RestaurantEntity?

    suspend fun getAllUserLikedRestaurantList() : List<RestaurantEntity>

    suspend fun insertUserLikedRestaurant(restaurantEntity: RestaurantEntity)

    suspend fun deleteUserLikedRestaurant(restaurantTitle: String)

    suspend fun deleteAllUserLikedRestaurant()
}