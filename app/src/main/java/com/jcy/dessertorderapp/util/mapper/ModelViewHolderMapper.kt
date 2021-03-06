package com.jcy.dessertorderapp.util.mapper

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jcy.dessertorderapp.databinding.*
import com.jcy.dessertorderapp.model.CellType
import com.jcy.dessertorderapp.model.Model
import com.jcy.dessertorderapp.screen.base.BaseViewModel
import com.jcy.dessertorderapp.util.provider.ResourceProvider
import com.jcy.dessertorderapp.widget.adapter.viewholder.EmptyViewHolder
import com.jcy.dessertorderapp.widget.adapter.viewholder.ModelViewHolder
import com.jcy.dessertorderapp.widget.adapter.viewholder.food.FoodMenuViewHolder
import com.jcy.dessertorderapp.widget.adapter.viewholder.order.OrderMenuViewHolder
import com.jcy.dessertorderapp.widget.adapter.viewholder.order.OrderViewHolder
import com.jcy.dessertorderapp.widget.adapter.viewholder.restaurant.LikeRestaurantViewHolder
import com.jcy.dessertorderapp.widget.adapter.viewholder.restaurant.RestaurantViewHolder
import com.jcy.dessertorderapp.widget.adapter.viewholder.review.RestaurantReviewViewHolder

object ModelViewHolderMapper {

    @Suppress("UNCHECKED_CAST")
    fun <M: Model> map(
        parent: ViewGroup,
        type: CellType,
        viewModel: BaseViewModel,
        resourceProvider: ResourceProvider
    ): ModelViewHolder<M> {

        val inflater = LayoutInflater.from(parent.context)
        val viewHolder = when(type){
            CellType.EMPTY_CELL -> EmptyViewHolder(
                ViewholderEmptyBinding.inflate(inflater,parent,false),
                viewModel,
                resourceProvider
            )
            CellType.RESTAURANT_CELL -> RestaurantViewHolder(
                ViewholderRestaurantBinding.inflate(inflater, parent, false),
                viewModel,
                resourceProvider
            )
            CellType.FOOD_CELL -> FoodMenuViewHolder(
                ViewholderFoodMenuBinding.inflate(inflater, parent, false),
                viewModel,
                resourceProvider
            )
            CellType.REVIEW_CELL -> RestaurantReviewViewHolder(
                ViewholderRestaurantReviewBinding.inflate(inflater, parent,false),
                viewModel,
                resourceProvider
            )
            CellType.LIKE_RESTAURANT_CELL -> LikeRestaurantViewHolder(
                ViewholderLikeRestaurantBinding.inflate(inflater, parent, false),
                viewModel,
                resourceProvider
            )
            CellType.ORDER_FOOD_CELL -> OrderMenuViewHolder(
                ViewholderOrderMenuBinding.inflate(inflater, parent, false),
                viewModel,
                resourceProvider
            )
            CellType.ORDER_CELL -> OrderViewHolder(
                ViewholderOrderBinding.inflate(inflater, parent, false),
                viewModel,
                resourceProvider
            )
        }
        return viewHolder as ModelViewHolder<M>
    }
}