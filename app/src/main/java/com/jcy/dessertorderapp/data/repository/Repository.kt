package com.jcy.dessertorderapp.data.repository

import com.jcy.dessertorderapp.data.entity.LocationLatLngEntity
import com.jcy.dessertorderapp.data.entity.RestaurantEntity
import com.jcy.dessertorderapp.screen.main.restaurant.RestaurantCategory

interface RestaurantRepository {
    suspend fun getList(
        restaurantCategory: RestaurantCategory,
        locationLatLngEntity: LocationLatLngEntity
    ): List<RestaurantEntity>
}