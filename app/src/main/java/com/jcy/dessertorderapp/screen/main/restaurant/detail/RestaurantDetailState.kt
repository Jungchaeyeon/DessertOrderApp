package com.jcy.dessertorderapp.screen.main.restaurant.detail

import com.jcy.dessertorderapp.data.entity.RestaurantEntity

sealed class RestaurantDetailState {
    object Uninitialized: RestaurantDetailState()

    object Loading: RestaurantDetailState()

    data class Success(
        val restaurantEntity: RestaurantEntity,
        val isLiked : Boolean? = null
    ): RestaurantDetailState()
}