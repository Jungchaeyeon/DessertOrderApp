package com.jcy.dessertorderapp.data.repository.restaurant.food

import com.jcy.dessertorderapp.data.db.dao.FoodMenuListDao
import com.jcy.dessertorderapp.data.entity.RestaurantFoodEntity
import com.jcy.dessertorderapp.data.network.FoodApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultRestaurantFoodRepository(

    private val foodApiService: FoodApiService,
    private val foodMenuBasketDao: FoodMenuListDao,
    private val ioDispatcher: CoroutineDispatcher
) : RestaurantFoodRepository{

    override suspend fun getFoods(restaurantId: Long): List<RestaurantFoodEntity> = withContext(ioDispatcher){
        val response = foodApiService.getRestaurantFoods(restaurantId)
        if(response.isSuccessful){
            response.body()?.map { it.toEntity(restaurantId) } ?: listOf()
        }else{
            listOf()
        }
    }

    override suspend fun getAllFoodMenuListInBasket(): List<RestaurantFoodEntity> = withContext(ioDispatcher){
        foodMenuBasketDao.getAll()
    }

    override suspend fun getFoodMenuListInBasket(restaurantId: Long): List<RestaurantFoodEntity> = withContext(ioDispatcher){
        foodMenuBasketDao.getAllByRestaurantId(restaurantId)
    }

    override suspend fun insertFoodmenuInBasket(restaurantFoodEntity: RestaurantFoodEntity) = withContext(ioDispatcher){
        foodMenuBasketDao.insert(restaurantFoodEntity)
    }

    override suspend fun removeFoodMenuListInBasket(foodId: String) = withContext(ioDispatcher){
        foodMenuBasketDao.delete(foodId)
    }

    override suspend fun clearFoodMenuListInBasket()  =withContext(ioDispatcher){
        foodMenuBasketDao.deleteAll()
    }

}