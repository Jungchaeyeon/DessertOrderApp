package com.jcy.dessertorderapp.data.db.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jcy.dessertorderapp.data.entity.LocationLatLngEntity
import com.jcy.dessertorderapp.data.entity.RestaurantEntity


@Database(
    entities = [LocationLatLngEntity::class, RestaurantEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ApplicationDatabase : RoomDatabase(){
    companion object{
        const val DB_NAME="ApplicationDatabase.db"
    }
    abstract fun LocationDao(): LocationDao

    abstract fun RestaurantDao() : RestaurantDao
}