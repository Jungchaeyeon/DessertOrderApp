package com.jcy.dessertorderapp.data.entity

import android.os.Parcelable
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.jcy.dessertorderapp.screen.main.restaurant.RestaurantCategory
import com.jcy.dessertorderapp.util.convertor.RoomTypeConvertors
import kotlinx.android.parcel.Parcelize

@androidx.room.Entity
@Parcelize
@TypeConverters(RoomTypeConvertors::class)
data class RestaurantEntity(
    override val id: Long,
    val restaurantInfoId: Long, //mocking data api id
    val restaurantCategory: RestaurantCategory,
    @PrimaryKey val restaurantTitle : String,
    val restaurantImageUrl: String,
    val grade: Float,
    val reviewCount: Int,
    val deliveryTimeRange: Pair<Int,Int>,
    val deliveryTipRange: Pair<Int,Int>,
    val restaurantTelNumber : String?
): Entity, Parcelable