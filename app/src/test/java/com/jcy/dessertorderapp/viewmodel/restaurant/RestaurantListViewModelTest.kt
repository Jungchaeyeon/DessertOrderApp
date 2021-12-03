package com.jcy.dessertorderapp.viewmodel.restaurant

import com.jcy.dessertorderapp.data.entity.LocationLatLngEntity
import com.jcy.dessertorderapp.data.repository.RestaurantRepository
import com.jcy.dessertorderapp.model.restaurant.RestaurantModel
import com.jcy.dessertorderapp.screen.main.restaurant.RestaurantCategory
import com.jcy.dessertorderapp.screen.main.restaurant.RestaurantListViewModel
import com.jcy.dessertorderapp.screen.main.restaurant.RestaurantOrder
import com.jcy.dessertorderapp.viewmodel.ViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
internal class RestaurantListViewModelTest : ViewModelTest(){
    /**
     * [RestaurantCategory]
     * [LocationLatLngEntity]
     * */

    private var restaurantCategory = RestaurantCategory.ALL

    private val locationLatLngEntity: LocationLatLngEntity = LocationLatLngEntity(0.0,0.0)

    private val restaurantRepository by inject<RestaurantRepository>()

    private val restaurantListViewModel by inject<RestaurantListViewModel>{
        parametersOf(
            restaurantCategory,
            locationLatLngEntity
        )
    }
    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @Test
    fun `test load restaurant list category ALL`() = runBlockingTest {
        val testObservable = restaurantListViewModel.restaurantListLiveData.test()

        restaurantListViewModel.fetchData()

        testObservable.assertValueSequence(
            listOf(
                restaurantRepository.getList(restaurantCategory, locationLatLngEntity).map{
                    RestaurantModel(
                        id= it.id,
                        restaurantInfoId = it.restaurantInfoId,
                        restaurantCategory = it.restaurantCategory,
                        restaurantTitle = it.restaurantTitle,
                        restaurantImageUrl = it.restaurantImageUrl,
                        grade = it.grade,
                        reviewCount = it.reviewCount,
                        deliveryTimeRange = it.deliveryTimeRange,
                        deliveryTipRange = it.deliveryTipRange,
                        restaurantTelNumber = it.restaurantTelNumber
                    )
                }
            )
        )
    }
    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @Test
    fun `test load restaurant list category Excepted`() = runBlockingTest {
        restaurantCategory = RestaurantCategory.CAFE_DESSERT

        val testObservable = restaurantListViewModel.restaurantListLiveData.test()

        restaurantListViewModel.fetchData()

        testObservable.assertValueSequence(
            listOf(
                listOf()
            )
        )

    }
    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @Test
    fun `test load restaurant list order by fast delivery time`() = runBlockingTest {
        val testObservable = restaurantListViewModel.restaurantListLiveData.test()

        restaurantListViewModel.setRestaurantOrder(RestaurantOrder.FAST_DELIVERY)

        testObservable.assertValueSequence(
            listOf(
                restaurantRepository.getList(restaurantCategory, locationLatLngEntity)
                    .sortedBy { it.deliveryTimeRange.first }
                    .map {
                        RestaurantModel(
                            id= it.id,
                            restaurantInfoId = it.restaurantInfoId,
                            restaurantCategory = it.restaurantCategory,
                            restaurantTitle = it.restaurantTitle,
                            restaurantImageUrl = it.restaurantImageUrl,
                            grade = it.grade,
                            reviewCount = it.reviewCount,
                            deliveryTimeRange = it.deliveryTimeRange,
                            deliveryTipRange = it.deliveryTipRange,
                            restaurantTelNumber = it.restaurantTelNumber
                        )
                    }
            )
        )
    }

}