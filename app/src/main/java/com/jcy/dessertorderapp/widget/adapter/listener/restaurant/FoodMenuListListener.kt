package com.jcy.dessertorderapp.widget.adapter.listener.restaurant

import com.jcy.dessertorderapp.model.restaurant.food.FoodModel
import com.jcy.dessertorderapp.widget.adapter.listener.AdapterListener

interface FoodMenuListListener : AdapterListener {
    fun onClickItem(model: FoodModel)
}