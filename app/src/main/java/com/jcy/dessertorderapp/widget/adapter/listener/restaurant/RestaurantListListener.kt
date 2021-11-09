package com.jcy.dessertorderapp.widget.adapter.listener.restaurant

import com.jcy.dessertorderapp.model.restaurant.RestaurantModel
import com.jcy.dessertorderapp.widget.adapter.listener.AdapterListener

interface RestaurantListListener : AdapterListener{
    fun onClickItem(model: RestaurantModel)
}