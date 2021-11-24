package com.jcy.dessertorderapp.widget.adapter.viewholder.review

import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.jcy.dessertorderapp.R
import com.jcy.dessertorderapp.databinding.ViewholderEmptyBinding
import com.jcy.dessertorderapp.databinding.ViewholderRestaurantBinding
import com.jcy.dessertorderapp.databinding.ViewholderRestaurantReviewBinding
import com.jcy.dessertorderapp.ext.clear
import com.jcy.dessertorderapp.ext.load
import com.jcy.dessertorderapp.model.Model
import com.jcy.dessertorderapp.model.restaurant.RestaurantModel
import com.jcy.dessertorderapp.model.restaurant.review.RestaurantReviewModel
import com.jcy.dessertorderapp.screen.base.BaseViewModel
import com.jcy.dessertorderapp.util.provider.ResourceProvider
import com.jcy.dessertorderapp.widget.adapter.listener.AdapterListener
import com.jcy.dessertorderapp.widget.adapter.listener.restaurant.RestaurantListListener
import com.jcy.dessertorderapp.widget.adapter.viewholder.ModelViewHolder

class RestaurantReviewViewHolder(
    private val binding: ViewholderRestaurantReviewBinding,
    viewModel: BaseViewModel,
    resourceProvider: ResourceProvider
) : ModelViewHolder<RestaurantReviewModel>(binding,viewModel,resourceProvider) {
    override fun reset() = with(binding){
        reviewThumbnailImage.clear()
        reviewThumbnailImage.isGone = true
    }

    override fun bindData(model: RestaurantReviewModel) {
        super.bindData(model)
        with(binding){
            if(model.thumbnailImageUri != null){
                reviewThumbnailImage.isVisible = true
                reviewThumbnailImage.load(model.thumbnailImageUri.toString(), 24f)
            }else{
                reviewThumbnailImage.isGone = true
            }
            reviewTitleText.text = model.title
            reviewText.text = model.description
            ratingBar.rating = model.grade
        }
    }

    override fun bindViews(model: RestaurantReviewModel, adapterListener: AdapterListener) = Unit
}