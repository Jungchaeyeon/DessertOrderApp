package com.jcy.dessertorderapp.data.repository.user

import com.jcy.dessertorderapp.data.db.dao.LocationDao
import com.jcy.dessertorderapp.data.db.dao.RestaurantDao
import com.jcy.dessertorderapp.data.entity.LocationLatLngEntity
import com.jcy.dessertorderapp.data.entity.RestaurantEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultUserRepository(
    private val locationDao: LocationDao,
    private val restaurantDao: RestaurantDao,
    private val ioDispatcher: CoroutineDispatcher
): UserRepository {
    override suspend fun getUserLocation(): LocationLatLngEntity?= withContext(ioDispatcher){
        locationDao.get(-1)
    }

    override suspend fun insertUserLocation(locationlatLngEntity: LocationLatLngEntity) = withContext(ioDispatcher){
        locationDao.insert(locationlatLngEntity)
    }

    override suspend fun getUserLikedRestaurant(restaurantTitle: String): RestaurantEntity? = withContext(ioDispatcher){
        restaurantDao.get(restaurantTitle)
    }

    override suspend fun insertUserLikedRestaurant(restaurantEntity: RestaurantEntity) = withContext(ioDispatcher){
        restaurantDao.insert(restaurantEntity)
    }

    override suspend fun deleteUserLikedRestaurant(restaurantTitle: String) {
        restaurantDao.delete(restaurantTitle)
    }

    override suspend fun deleteAllUserLikedRestaurant() {
        restaurantDao.deleteAll()
    }
}