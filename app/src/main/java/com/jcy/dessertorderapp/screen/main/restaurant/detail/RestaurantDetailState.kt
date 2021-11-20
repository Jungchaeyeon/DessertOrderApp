package com.jcy.dessertorderapp.screen.main.restaurant.detail

import com.jcy.dessertorderapp.data.entity.RestaurantEntity
import com.jcy.dessertorderapp.data.entity.RestaurantFoodEntity

sealed class RestaurantDetailState {
    object Uninitialized: RestaurantDetailState()

    object Loading: RestaurantDetailState()

    data class Success(
        val restaurantEntity: RestaurantEntity,
        val restaurantFoodList: List<RestaurantFoodEntity> ?= null,
        val foodMenuListInBasket: List<RestaurantFoodEntity> ?= null,
        val isClearNeedInBasketAndAction: Pair<Boolean, ()->Unit> = Pair(false,{}),
        val isLiked : Boolean? = null
    ): RestaurantDetailState()
}