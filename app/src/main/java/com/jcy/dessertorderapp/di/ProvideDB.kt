package com.jcy.dessertorderapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jcy.dessertorderapp.data.db.dao.ApplicationDatabase

fun provideDB(context: Context): ApplicationDatabase =
    Room.databaseBuilder(context, ApplicationDatabase::class.java, ApplicationDatabase.DB_NAME)
        .fallbackToDestructiveMigration() // 기존 데이터를 버리고 다음 버전으로 넘어감
        .build()

fun provideLocationDao(database: ApplicationDatabase) = database.LocationDao()

fun provideRestaurantDao(database: ApplicationDatabase) = database.RestaurantDao()

fun provideFoodMenuBasketDao(database: ApplicationDatabase) = database.FoodMenuBasketDao()



