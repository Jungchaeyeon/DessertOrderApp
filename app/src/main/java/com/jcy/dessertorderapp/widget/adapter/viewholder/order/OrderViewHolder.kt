package com.jcy.dessertorderapp.widget.adapter.viewholder.order

import com.jcy.dessertorderapp.R
import com.jcy.dessertorderapp.databinding.ViewholderOrderBinding
import com.jcy.dessertorderapp.databinding.ViewholderOrderMenuBinding
import com.jcy.dessertorderapp.model.restaurant.order.OrderModel
import com.jcy.dessertorderapp.screen.base.BaseViewModel
import com.jcy.dessertorderapp.util.provider.ResourceProvider
import com.jcy.dessertorderapp.widget.adapter.listener.AdapterListener
import com.jcy.dessertorderapp.widget.adapter.listener.order.OrderListListener
import com.jcy.dessertorderapp.widget.adapter.viewholder.ModelViewHolder

class OrderViewHolder(
    private val binding: ViewholderOrderBinding,
    viewModel : BaseViewModel,
    resourceProvider: ResourceProvider
) : ModelViewHolder<OrderModel>(binding, viewModel, resourceProvider){

    override fun reset() = Unit

    override fun bindData(model: OrderModel) {
        super.bindData(model)
        with(binding){
            orderTitleText.text = resourceProvider.getString(R.string.order_history_title, model.orderId)

            val foodMenuList = model.foodMenuList
            foodMenuList.groupBy { it.title }
                .entries.forEach{(title, menuList) ->
                    val orderdataStr =
                        orderContentText.text.toString() + "메뉴 : $title | 가격 : ${menuList.first().price}원 X ${menuList.size}\n"
                    orderContentText.text = orderdataStr
                }
            orderContentText.text = orderContentText.text.trim()

            orderTotalPriceText.text = resourceProvider.getString(R.string.price, foodMenuList.map { it.price }.reduce{total, price -> total+ price})
        }
    }
    override fun bindViews(model: OrderModel, adapterListener: AdapterListener) {
        if(adapterListener is OrderListListener){
                binding.root.setOnClickListener {
                    adapterListener.writeRestaurantReview(model.orderId, model.restaurantTitle)
            }
        }
    }
}