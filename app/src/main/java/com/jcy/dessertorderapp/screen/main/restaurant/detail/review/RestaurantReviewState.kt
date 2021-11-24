package com.jcy.dessertorderapp.screen.main.restaurant.detail.review

import com.jcy.dessertorderapp.model.restaurant.review.RestaurantReviewModel

sealed class RestaurantReviewState {

    object Uninitialized : RestaurantReviewState()

    object Loading: RestaurantReviewState()

    data class Success(
        val reviewList: List<RestaurantReviewModel>
    ): RestaurantReviewState()
}
