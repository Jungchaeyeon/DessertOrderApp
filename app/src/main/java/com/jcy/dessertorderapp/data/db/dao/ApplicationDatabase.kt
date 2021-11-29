package com.jcy.dessertorderapp.data.db.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jcy.dessertorderapp.data.entity.LocationLatLngEntity
import com.jcy.dessertorderapp.data.entity.RestaurantEntity
import com.jcy.dessertorderapp.data.entity.RestaurantFoodEntity


@Database(
    entities = [LocationLatLngEntity::class, RestaurantEntity::class, RestaurantFoodEntity::class],
    version = 2,
    exportSchema = false
)
abstract class ApplicationDatabase : RoomDatabase(){
    companion object{
        const val DB_NAME="ApplicationDatabase.db"
    }
    abstract fun LocationDao(): LocationDao

    abstract fun RestaurantDao() : RestaurantDao

    abstract fun FoodMenuBasketDao() : FoodMenuListDao

}