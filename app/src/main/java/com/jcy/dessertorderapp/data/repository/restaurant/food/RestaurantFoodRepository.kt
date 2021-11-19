package com.jcy.dessertorderapp.data.repository.restaurant.food

import com.jcy.dessertorderapp.data.entity.RestaurantFoodEntity

interface RestaurantFoodRepository {

    suspend fun getFoods(restaurantId: Long): List<RestaurantFoodEntity>

   // suspend fun getAllFoodMenuList
}