package com.jcy.dessertorderapp.data

import com.jcy.dessertorderapp.data.entity.LocationLatLngEntity
import com.jcy.dessertorderapp.data.entity.RestaurantEntity
import com.jcy.dessertorderapp.data.repository.RestaurantRepository
import com.jcy.dessertorderapp.screen.main.restaurant.RestaurantCategory

class TestRestaurantRepository: RestaurantRepository {
    override suspend fun getList(
        restaurantCategory: RestaurantCategory,
        locationLatLngEntity: LocationLatLngEntity
    ): List<RestaurantEntity> {
        return when (restaurantCategory) {
            RestaurantCategory.CAFE_DESSERT -> {
                listOf(
                    RestaurantEntity(
                        id = 0,
                        restaurantInfoId = 0,
                        restaurantCategory = RestaurantCategory.CAFE_DESSERT,
                        restaurantTitle = "마포화로집",
                        restaurantImageUrl = "https://picsum.photos/200",
                        grade = 4.3f,
                        reviewCount = 100,
                        deliveryTimeRange = Pair(5, 20),
                        deliveryTipRange = Pair(0, 2000),
                        restaurantTelNumber = "02-1234-5678"
                    ),
                    RestaurantEntity(
                        id = 1,
                        restaurantInfoId = 1,
                        restaurantCategory = RestaurantCategory.CAFE_DESSERT,
                        restaurantTitle = "옛날우동&덮밥",
                        restaurantImageUrl = "https://picsum.photos/200",
                        grade = 2.1f,
                        reviewCount = 100,
                        deliveryTimeRange = Pair(8, 20),
                        deliveryTipRange = Pair(0, 2000),
                        restaurantTelNumber = "02-1234-5678"
                    ),
                    RestaurantEntity(
                        id = 2,
                        restaurantInfoId = 2,
                        restaurantCategory = RestaurantCategory.CAFE_DESSERT,
                        restaurantTitle = "마스터석쇠불고기&냉면plus",
                        restaurantImageUrl = "https://picsum.photos/200",
                        grade = 5f,
                        reviewCount = 100,
                        deliveryTimeRange = Pair(1, 20),
                        deliveryTipRange = Pair(0, 2000),
                        restaurantTelNumber = "02-1234-5678"
                    ),
                    RestaurantEntity(
                        id = 3,
                        restaurantInfoId = 3,
                        restaurantCategory = RestaurantCategory.CAFE_DESSERT,
                        restaurantTitle = "마스터통삼겹",
                        restaurantImageUrl = "https://picsum.photos/200",
                        grade = 4.1f,
                        reviewCount = 100,
                        deliveryTimeRange = Pair(4, 20),
                        deliveryTipRange = Pair(0, 2000),
                        restaurantTelNumber = "02-1234-5678"
                    ),
                    RestaurantEntity(
                        id = 4,
                        restaurantInfoId = 4,
                        restaurantCategory = RestaurantCategory.CAFE_DESSERT,
                        restaurantTitle = "창영이 족발&보쌈",
                        restaurantImageUrl = "https://picsum.photos/200",
                        grade = 3.4f,
                        reviewCount = 100,
                        deliveryTimeRange = Pair(10, 20),
                        deliveryTipRange = Pair(0, 2000),
                        restaurantTelNumber = "02-1234-5678"
                    ),
                    RestaurantEntity(
                        id = 5,
                        restaurantInfoId = 5,
                        restaurantCategory = RestaurantCategory.CAFE_DESSERT,
                        restaurantTitle = "콩나물국밥&코다리조림 콩심 인천논현점",
                        restaurantImageUrl = "https://picsum.photos/200",
                        grade = 2.4f,
                        reviewCount = 100,
                        deliveryTimeRange = Pair(14, 20),
                        deliveryTipRange = Pair(0, 2000),
                        restaurantTelNumber = "02-1234-5678"
                    ),
                    RestaurantEntity(
                        id = 6,
                        restaurantInfoId = 6,
                        restaurantCategory = RestaurantCategory.CAFE_DESSERT,
                        restaurantTitle = "김여사 칼국수&냉면 논현점",
                        restaurantImageUrl = "https://picsum.photos/200",
                        grade = 3.4f,
                        reviewCount = 100,
                        deliveryTimeRange = Pair(2, 20),
                        deliveryTipRange = Pair(0, 2000),
                        restaurantTelNumber = "02-1234-5678"
                    ),
                    RestaurantEntity(
                        id = 7,
                        restaurantInfoId = 7,
                        restaurantCategory = RestaurantCategory.CAFE_DESSERT,
                        restaurantTitle = "돈키호테",
                        restaurantImageUrl = "https://picsum.photos/200",
                        grade = 1.3f,
                        reviewCount = 100,
                        deliveryTimeRange = Pair(7, 20),
                        deliveryTipRange = Pair(0, 2000),
                        restaurantTelNumber = "02-1234-5678"
                    ),
                )
            }
            else -> {
                listOf()
            }
        }
    }
}