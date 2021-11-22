package com.jcy.dessertorderapp.screen.main.restaurant.detail.review

import com.jcy.dessertorderapp.data.entity.RestaurantReviewEntity

sealed class RestaurantReviewState {

    object Uninitialized : RestaurantReviewState()

    object Loading: RestaurantReviewState()

    data class Success(
        val reviewList: List<RestaurantReviewEntity>
    ): RestaurantReviewState()
}
