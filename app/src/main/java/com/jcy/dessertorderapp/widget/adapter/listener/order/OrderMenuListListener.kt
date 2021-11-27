package com.jcy.dessertorderapp.widget.adapter.listener.order

import com.jcy.dessertorderapp.model.restaurant.food.FoodModel
import com.jcy.dessertorderapp.widget.adapter.listener.AdapterListener

interface OrderMenuListListener: AdapterListener {
    fun onRemoveItem(model: FoodModel)
}