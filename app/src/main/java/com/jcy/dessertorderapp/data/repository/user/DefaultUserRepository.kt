package com.jcy.dessertorderapp.data.repository.user

import com.jcy.dessertorderapp.data.db.dao.LocationDao
import com.jcy.dessertorderapp.data.entity.LocationLatLngEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultUserRepository(
    private val locationDao: LocationDao,
    private val ioDispatcher: CoroutineDispatcher
): UserRepository {
    override suspend fun getUserLocation(): LocationLatLngEntity?= withContext(ioDispatcher){
        locationDao.get(-1)
    }

    override suspend fun insertUserLocation(locationlatLngEntity: LocationLatLngEntity){
        locationDao.insert(locationlatLngEntity)
    }
}