package com.jcy.dessertorderapp.model.restaurant.food

import com.jcy.dessertorderapp.model.CellType
import com.jcy.dessertorderapp.model.Model

data class FoodModel(
    override val id: Long,
    override val type: CellType = CellType.FOOD_CELL,
    val title: String,
    val description: String,
    val price: Int,
    val imageUrl: String,
    val restaurantId: Long,
): Model(id, type)
