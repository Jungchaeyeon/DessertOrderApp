package com.jcy.dessertorderapp.data.repository.restaurant.review

import com.jcy.dessertorderapp.data.entity.RestaurantReviewEntity

interface RestaurantReviewRepository {

    suspend fun getReviews(restaurantTitle: String) : List<RestaurantReviewEntity>
}