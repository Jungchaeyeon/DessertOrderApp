package com.jcy.dessertorderapp.screen.main.restaurant.detail.review

import androidx.core.os.bundleOf
import com.jcy.dessertorderapp.data.entity.RestaurantFoodEntity
import com.jcy.dessertorderapp.databinding.FragmentMenuListBinding
import com.jcy.dessertorderapp.screen.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class RestaurantReviewListFragment :
    BaseFragment<RestaurantReviewListViewModel, FragmentMenuListBinding>() {
    override val viewModel by viewModel<RestaurantReviewListViewModel>()

    override fun getViewBinding(): FragmentMenuListBinding = FragmentMenuListBinding.inflate(layoutInflater)

    override fun observeData() {

    }
    companion object{

        const val RESTAURANT_ID_KEY ="restaurantId"

        fun newInstance(restaurantTitle: String): RestaurantReviewListFragment{
            val bundle = bundleOf(
                RESTAURANT_ID_KEY to restaurantTitle,
            )
            return RestaurantReviewListFragment().apply {
                arguments = bundle
            }
        }
    }
}