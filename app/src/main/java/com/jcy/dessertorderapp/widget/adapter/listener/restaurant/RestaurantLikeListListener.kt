package com.jcy.dessertorderapp.widget.adapter.listener.restaurant

import com.jcy.dessertorderapp.model.restaurant.RestaurantModel

interface RestaurantLikeListListener : RestaurantListListener{

    fun onDislikeItem(model : RestaurantModel)

}