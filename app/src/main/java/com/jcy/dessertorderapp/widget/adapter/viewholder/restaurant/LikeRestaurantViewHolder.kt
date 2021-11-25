package com.jcy.dessertorderapp.widget.adapter.viewholder.restaurant

import com.jcy.dessertorderapp.R
import com.jcy.dessertorderapp.databinding.ViewholderLikeRestaurantBinding
import com.jcy.dessertorderapp.databinding.ViewholderRestaurantBinding
import com.jcy.dessertorderapp.ext.clear
import com.jcy.dessertorderapp.ext.load
import com.jcy.dessertorderapp.model.restaurant.RestaurantModel
import com.jcy.dessertorderapp.screen.base.BaseViewModel
import com.jcy.dessertorderapp.util.provider.ResourceProvider
import com.jcy.dessertorderapp.widget.adapter.listener.AdapterListener
import com.jcy.dessertorderapp.widget.adapter.listener.restaurant.RestaurantLikeListListener
import com.jcy.dessertorderapp.widget.adapter.viewholder.ModelViewHolder

class LikeRestaurantViewHolder(
    private val binding: ViewholderLikeRestaurantBinding,
    viewModel: BaseViewModel,
    resourceProvider: ResourceProvider
) : ModelViewHolder<RestaurantModel>(binding,viewModel,resourceProvider) {
    override fun reset() = with(binding){
        restaurantImage.clear()
    }

    override fun bindData(model: RestaurantModel) {
        super.bindData(model)
        with(binding){
            restaurantImage.load(model.restaurantImageUrl, 24f)
            restaurantTitleText.text = model.restaurantTitle
            gradeText.text = resourceProvider.getString(R.string.grade_format, model.grade)
            reviewCountText.text = resourceProvider.getString(R.string.review_count, model.reviewCount)
            val (minTime, maxTime) = model.deliveryTimeRange
            deliveryTimeText.text = resourceProvider.getString(R.string.delivery_time, minTime, maxTime)

            val (minTip, maxTip) = model.deliveryTipRange
            deliveryTipText.text = resourceProvider.getString(R.string.delivery_tip, minTip, maxTip)
        }
    }

    override fun bindViews(model: RestaurantModel, adapterListener: AdapterListener) = with(binding){
       if(adapterListener is RestaurantLikeListListener){
          root.setOnClickListener {
              adapterListener.onClickItem(model)
          }
           likeImageBtn.setOnClickListener {
               adapterListener.onDislikeItem(model)
           }
       }
    }
}