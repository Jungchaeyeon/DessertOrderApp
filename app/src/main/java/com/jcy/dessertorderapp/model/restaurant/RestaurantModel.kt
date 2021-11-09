package com.jcy.dessertorderapp.model.restaurant

import com.jcy.dessertorderapp.data.entity.RestaurantEntity
import com.jcy.dessertorderapp.model.CellType
import com.jcy.dessertorderapp.model.Model
import com.jcy.dessertorderapp.screen.main.restaurant.RestaurantCategory

data class RestaurantModel(
    override val id: Long,
    override val type: CellType = CellType.RESTAURANT_CELL,
    val restaurantInfoId: Long, //mocking data api id
    val restaurantCategory: RestaurantCategory,
    val restaurantTitle : String,
    val restaurantImageUrl: String,
    val grade: Float,
    val reviewCount: Int,
    val deliveryTimeRange: Pair<Int,Int>,
    val deliveryTipRange: Pair<Int,Int>
): Model(id, type){
    fun toEntity() = RestaurantEntity(
        id,
        restaurantInfoId,
        restaurantCategory,
        restaurantTitle,
        restaurantImageUrl,
        grade,
        reviewCount,
        deliveryTimeRange,
        deliveryTipRange
    )
}