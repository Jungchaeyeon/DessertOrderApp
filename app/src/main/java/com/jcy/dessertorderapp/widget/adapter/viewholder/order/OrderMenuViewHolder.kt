package com.jcy.dessertorderapp.widget.adapter.viewholder.order

import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.jcy.dessertorderapp.R
import com.jcy.dessertorderapp.databinding.ViewholderFoodMenuBinding
import com.jcy.dessertorderapp.databinding.ViewholderOrderMenuBinding
import com.jcy.dessertorderapp.ext.clear
import com.jcy.dessertorderapp.ext.load
import com.jcy.dessertorderapp.model.restaurant.food.FoodModel
import com.jcy.dessertorderapp.screen.base.BaseViewModel
import com.jcy.dessertorderapp.screen.main.restaurant.detail.menu.RestaurantMenuListViewModel
import com.jcy.dessertorderapp.util.provider.ResourceProvider
import com.jcy.dessertorderapp.widget.adapter.listener.AdapterListener
import com.jcy.dessertorderapp.widget.adapter.listener.order.OrderMenuListListener
import com.jcy.dessertorderapp.widget.adapter.listener.restaurant.FoodMenuListListener
import com.jcy.dessertorderapp.widget.adapter.viewholder.ModelViewHolder

class OrderMenuViewHolder(
    private val binding: ViewholderOrderMenuBinding,
    viewModel : BaseViewModel,
    resourceProvider: ResourceProvider
) : ModelViewHolder<FoodModel>(binding, viewModel, resourceProvider){

    override fun reset() = with(binding){
        foodImage.clear()
    }

    override fun bindData(model: FoodModel) {
        super.bindData(model)
        with(binding){
            foodImage.load(model.imageUrl, 24f, CenterCrop())
            foodTitleText.text = model.title
            foodDescriptionText.text = model.description
            priceText.text = resourceProvider.getString(R.string.price, model.price)
        }
    }
    override fun bindViews(model: FoodModel, adapterListener: AdapterListener) {
        if(adapterListener is OrderMenuListListener){
            binding.closeBtn.setOnClickListener {
                adapterListener.onRemoveItem(model)
            }
        }
    }
}