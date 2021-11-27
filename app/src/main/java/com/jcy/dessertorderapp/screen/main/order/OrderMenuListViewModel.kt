package com.jcy.dessertorderapp.screen.main.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.jcy.dessertorderapp.data.repository.restaurant.food.RestaurantFoodRepository
import com.jcy.dessertorderapp.model.CellType
import com.jcy.dessertorderapp.model.restaurant.food.FoodModel
import com.jcy.dessertorderapp.screen.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OrderMenuListViewModel(
    private val restaurantFoodRepository: RestaurantFoodRepository
) : BaseViewModel() {

    private val firebaseActivity by lazy { FirebaseAuth.getInstance() }

    val orderMenuStateLiveData = MutableLiveData<OrderMenuState>(OrderMenuState.Uninitialized)

    override fun fetchData(): Job = viewModelScope.launch{
        orderMenuStateLiveData.value = OrderMenuState.Loading
        val foodMenuList = restaurantFoodRepository.getAllFoodMenuListInBasket()
        orderMenuStateLiveData.value = OrderMenuState.Success(
            foodMenuList.map{
                FoodModel(
                    id = it.hashCode().toLong(),
                    type= CellType.ORDER_FOOD_CELL,
                    title = it.title,
                    description = it.description,
                    price = it.price,
                    imageUrl = it.imageUrl,
                    restaurantId =  it.restaurantId,
                    foodId = it.id
                )
            }
        )
    }
    fun orderMenu() {

    }
    fun clearOrderMenu() {

    }

    fun removeOrderMenu(model: FoodModel) {

    }
}