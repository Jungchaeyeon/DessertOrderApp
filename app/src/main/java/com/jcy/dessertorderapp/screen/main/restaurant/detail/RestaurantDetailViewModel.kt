package com.jcy.dessertorderapp.screen.main.restaurant.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jcy.dessertorderapp.data.entity.RestaurantEntity
import com.jcy.dessertorderapp.screen.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RestaurantDetailViewModel(
    private val restaurantEntity: RestaurantEntity
) : BaseViewModel() {

    val restaurantDetailStateLiveData = MutableLiveData<RestaurantDetailState>(RestaurantDetailState.Uninitialized)

    override fun fetchData(): Job = viewModelScope.launch {
        restaurantDetailStateLiveData.value = RestaurantDetailState.Success(
            restaurantEntity= restaurantEntity
        )
    }
}