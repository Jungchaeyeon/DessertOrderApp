package com.jcy.dessertorderapp.data.entity

import android.net.Uri

data class RestaurantReviewEntity(
    override val id: Long,
    val title: String,
    val description: String,
    val grade: Float,
    val images: List<Uri>?= null
): Entity
