package com.jcy.dessertorderapp.model.restaurant.order

import com.jcy.dessertorderapp.data.entity.OrderEntity
import com.jcy.dessertorderapp.data.entity.RestaurantFoodEntity
import com.jcy.dessertorderapp.model.CellType
import com.jcy.dessertorderapp.model.Model

data class OrderModel(
    override val id: Long,
    override val type: CellType = CellType.ORDER_CELL,
    val orderId: String,
    val userId: String,
    val restaurantId: Long,
    val foodMenuList: List<RestaurantFoodEntity>
): Model(id, type){
    fun toEntity() = OrderEntity(
        id= orderId,
        userId= userId,
        restaurantId = restaurantId,
        foodMenuList = foodMenuList
    )
}
