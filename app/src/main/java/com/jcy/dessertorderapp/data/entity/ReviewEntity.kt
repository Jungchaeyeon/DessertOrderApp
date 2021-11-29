package com.jcy.dessertorderapp.data.entity

data class ReviewEntity(
    val userId: String,
    val title: String,
    val createAt: Long,
    val content: String,
    val rating: Float,
    val imageUrlList: List<String>? = null,
    val orderId: String,
    val restaurantTitle: String
)
