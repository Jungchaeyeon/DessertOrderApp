package com.jcy.dessertorderapp.model.restaurant.review

import android.net.Uri
import com.jcy.dessertorderapp.model.CellType
import com.jcy.dessertorderapp.model.Model

data class RestaurantReviewModel(
    override val id: Long,
    override val type: CellType = CellType.REVIEW_CELL,
    val title: String,
    val description: String,
    val grade: Float,
    val thumbnailImageUri: Uri?= null
): Model(id, type)
