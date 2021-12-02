package com.jcy.dessertorderapp.data.repository.restaurant.review

interface RestaurantReviewRepository {

    suspend fun getReviews(restaurantTitle: String) : DefaultRestaurantReviewRepository.Result
}