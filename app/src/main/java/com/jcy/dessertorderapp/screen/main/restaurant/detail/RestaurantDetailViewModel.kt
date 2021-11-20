package com.jcy.dessertorderapp.screen.main.restaurant.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jcy.dessertorderapp.data.entity.RestaurantEntity
import com.jcy.dessertorderapp.data.entity.RestaurantFoodEntity
import com.jcy.dessertorderapp.data.repository.restaurant.food.RestaurantFoodRepository
import com.jcy.dessertorderapp.data.repository.user.UserRepository
import com.jcy.dessertorderapp.screen.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RestaurantDetailViewModel(
    private val restaurantEntity: RestaurantEntity,
    private val restaurantFoodRepository: RestaurantFoodRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {

    val restaurantDetailStateLiveData = MutableLiveData<RestaurantDetailState>(RestaurantDetailState.Uninitialized)

    override fun fetchData(): Job = viewModelScope.launch {
        restaurantDetailStateLiveData.value = RestaurantDetailState.Success(
            restaurantEntity= restaurantEntity
        )
        restaurantDetailStateLiveData.value = RestaurantDetailState.Loading
        val foods = restaurantFoodRepository.getFoods(restaurantId = restaurantEntity.restaurantInfoId)
        val foodMenuListInBasket = restaurantFoodRepository.getAllFoodMenuListInBasket()
        val isLiked = userRepository.getUserLikedRestaurant(restaurantEntity.restaurantTitle) != null
        restaurantDetailStateLiveData.value = RestaurantDetailState.Success(
            restaurantEntity = restaurantEntity,
            restaurantFoodList = foods,
            foodMenuListInBasket= foodMenuListInBasket,
            isLiked = isLiked
        )
    }

    fun getRestaurantPhoneNumber(): String? {
        return when(val data = restaurantDetailStateLiveData.value){
            is RestaurantDetailState.Success ->{
                data.restaurantEntity.restaurantTelNumber
            }
            else -> null
        }
    }
    fun getRestaurantInfo(): RestaurantEntity? {
        return when (val data = restaurantDetailStateLiveData.value) {
            is RestaurantDetailState.Success -> {
                data.restaurantEntity
            }
            else -> null
        }
    }

    fun toggleLikedRestaurant() = viewModelScope.launch{
        when(val data = restaurantDetailStateLiveData.value){
            is RestaurantDetailState.Success -> {
                userRepository.getUserLikedRestaurant(restaurantEntity.restaurantTitle)?.let {
                    userRepository.deleteUserLikedRestaurant(it.restaurantTitle)
                    restaurantDetailStateLiveData.value = data.copy(
                        isLiked= false
                    )
                } ?: kotlin.run {
                    userRepository.insertUserLikedRestaurant(restaurantEntity)
                    restaurantDetailStateLiveData.value = data.copy(
                        isLiked= true
                    )
                }
            }
        }
    }

    //메뉴 추가 - add
    fun notifyFoodMenuListInBasket(foodMenu: RestaurantFoodEntity) = viewModelScope.launch{
        when(val data = restaurantDetailStateLiveData.value){
            is RestaurantDetailState.Success -> {
                restaurantDetailStateLiveData.value = data.copy(
                    foodMenuListInBasket = data.foodMenuListInBasket?.toMutableList()?.apply {
                        add(foodMenu)
                    }
                )
            }
            else -> Unit
        }
    }

    // 사용자에게 다른 식당의 메뉴 주문을 위해서는 기존 장바구니를 비워야함을 알리는 로직 :
    // Success 상태에서의 로직에서 알림을 주는 코드를 추가할 것이고, 그를 위해 Success data값을 변경해준다.
    fun notifyClearNeedAlertInBasket(clearNeed: Boolean, afterAction: () -> Unit)= viewModelScope.launch{
        when (val data = restaurantDetailStateLiveData.value) {
            is RestaurantDetailState.Success -> {
                restaurantDetailStateLiveData.value = data.copy(
                    isClearNeedInBasketAndAction = Pair(clearNeed, afterAction)
                )
            }
            else -> Unit
        }
    }

    fun notifyClearBasket() = viewModelScope.launch{
        when (val data = restaurantDetailStateLiveData.value) {
            is RestaurantDetailState.Success -> {
                restaurantDetailStateLiveData.value = data.copy(
                    foodMenuListInBasket = listOf(),
                    isClearNeedInBasketAndAction = Pair(false, {})
                )
            }
            else -> Unit
        }
    }
}