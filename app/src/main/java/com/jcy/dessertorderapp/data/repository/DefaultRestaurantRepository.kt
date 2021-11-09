package com.jcy.dessertorderapp.data.repository

import com.jcy.dessertorderapp.data.entity.RestaurantEntity
import com.jcy.dessertorderapp.screen.main.restaurant.RestaurantCategory
import com.jcy.dessertorderapp.util.provider.ResourceProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultRestaurantRepository (
    private val resourceProvider: ResourceProvider,
    private val ioDispatcher : CoroutineDispatcher
        ): RestaurantRepository{

    override suspend fun getList(
        restaurantCategory: RestaurantCategory
    ): List<RestaurantEntity> = withContext(ioDispatcher){
        //TODO API 를 통한 데이터 받아오기
      listOf(
            RestaurantEntity(
                id= 0,
                restaurantInfoId= 0,
                restaurantCategory = RestaurantCategory.ALL,
                restaurantTitle = "마포화로집",
                restaurantImageUrl = "https://picsum.photos/200",
                grade = (1 until 5).random() + ((0..10).random() / 10f),
                reviewCount = (0 until 200).random(),
                deliveryTimeRange = Pair(0,20),
                deliveryTipRange = Pair(0, 2000)
            ),
              RestaurantEntity(
                  id= 1,
                  restaurantInfoId= 1,
                  restaurantCategory = RestaurantCategory.ALL,
                  restaurantTitle = "옛날우동&덮밥",
                  restaurantImageUrl = "https://picsum.photos/200",
                  grade = (1 until 5).random() + ((0..10).random() / 10f),
                  reviewCount = (0 until 200).random(),
                  deliveryTimeRange = Pair(0,20),
                  deliveryTipRange = Pair(0, 2000)
              ),
              RestaurantEntity(
                  id= 2,
                  restaurantInfoId= 2,
                  restaurantCategory = RestaurantCategory.ALL,
                  restaurantTitle = "마스터석쇠불고기&냉면",
                  restaurantImageUrl = "https://picsum.photos/200",
                  grade = (1 until 5).random() + ((0..10).random() / 10f),
                  reviewCount = (0 until 200).random(),
                  deliveryTimeRange = Pair(0,20),
                  deliveryTipRange = Pair(0, 2000)
              ),
              RestaurantEntity(
                  id= 3,
                  restaurantInfoId= 3,
                  restaurantCategory = RestaurantCategory.ALL,
                  restaurantTitle = "창영이 족발&보쌈",
                  restaurantImageUrl = "https://picsum.photos/200",
                  grade = (1 until 5).random() + ((0..10).random() / 10f),
                  reviewCount = (0 until 200).random(),
                  deliveryTimeRange = Pair(0,20),
                  deliveryTipRange = Pair(0, 2000)
              ),
              RestaurantEntity(
                  id= 4,
                  restaurantInfoId= 4,
                  restaurantCategory = RestaurantCategory.ALL,
                  restaurantTitle = "콩나물국밥&코다리 조림 콩심 인천논현점",
                  restaurantImageUrl = "https://picsum.photos/200",
                  grade = (1 until 5).random() + ((0..10).random() / 10f),
                  reviewCount = (0 until 200).random(),
                  deliveryTimeRange = Pair(0,20),
                  deliveryTipRange = Pair(0, 2000)
              ),
              RestaurantEntity(
                  id= 5,
                  restaurantInfoId= 5,
                  restaurantCategory = RestaurantCategory.ALL,
                  restaurantTitle = "김여사 칼국수&냉면 논현점",
                  restaurantImageUrl = "https://picsum.photos/200",
                  grade = (1 until 5).random() + ((0..10).random() / 10f),
                  reviewCount = (0 until 200).random(),
                  deliveryTimeRange = Pair(0,20),
                  deliveryTipRange = Pair(0, 2000)
              ),
              RestaurantEntity(
                  id= 6,
                  restaurantInfoId= 6,
                  restaurantCategory = RestaurantCategory.ALL,
                  restaurantTitle = "돈키호테",
                  restaurantImageUrl = "https://picsum.photos/200",
                  grade = (1 until 5).random() + ((0..10).random() / 10f),
                  reviewCount = (0 until 200).random(),
                  deliveryTimeRange = Pair(0,20),
                  deliveryTipRange = Pair(0, 2000)
              )
      )
    }
}