package com.jcy.dessertorderapp.widget.adapter.listener.order

import com.jcy.dessertorderapp.widget.adapter.listener.AdapterListener

interface OrderListListener : AdapterListener{

    fun writeRestaurantReview(orderId: String, restaurantTitle: String)
}