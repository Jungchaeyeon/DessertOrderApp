package com.jcy.dessertorderapp.screen.main.like

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jcy.dessertorderapp.data.entity.RestaurantEntity
import com.jcy.dessertorderapp.data.repository.user.UserRepository
import com.jcy.dessertorderapp.model.CellType
import com.jcy.dessertorderapp.model.restaurant.RestaurantModel
import com.jcy.dessertorderapp.screen.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RestaurantLikeListViewModel(
    private val userRepository: UserRepository
) : BaseViewModel() {

    val restaurantListLiveData = MutableLiveData<List<RestaurantModel>>()

    override fun fetchData(): Job = viewModelScope.launch{
       restaurantListLiveData.value = userRepository.getAllUserLikedRestaurantList().map{
           RestaurantModel(
               id= it.id,
               type = CellType.LIKE_RESTAURANT_CELL,
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
    }
    fun dislikeRestaurant(restaurant: RestaurantEntity) =viewModelScope.launch{
        userRepository.deleteUserLikedRestaurant(restaurant.restaurantTitle)
        fetchData()
    }
}