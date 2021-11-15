package com.jcy.dessertorderapp.data.entity

import android.os.Parcelable
import com.jcy.dessertorderapp.screen.main.restaurant.RestaurantCategory
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RestaurantEntity(
    override val id: Long,
    val restaurantInfoId: Long, //mocking data api id
    val restaurantCategory: RestaurantCategory,
    val restaurantTitle : String,
    val restaurantImageUrl: String,
    val grade: Float,
    val reviewCount: Int,
    val deliveryTimeRange: Pair<Int,Int>,
    val deliveryTipRange: Pair<Int,Int>,
    val restaurantTelNumber : String?
): Entity, Parcelable