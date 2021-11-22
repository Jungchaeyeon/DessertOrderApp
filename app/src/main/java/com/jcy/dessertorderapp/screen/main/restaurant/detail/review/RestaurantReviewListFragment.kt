package com.jcy.dessertorderapp.screen.main.restaurant.detail.review

import android.widget.Toast
import androidx.core.os.bundleOf
import com.jcy.dessertorderapp.data.entity.RestaurantFoodEntity
import com.jcy.dessertorderapp.databinding.FragmentMenuListBinding
import com.jcy.dessertorderapp.screen.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RestaurantReviewListFragment :
    BaseFragment<RestaurantReviewListViewModel, FragmentMenuListBinding>() {
    override val viewModel by viewModel<RestaurantReviewListViewModel>{
        parametersOf(
            arguments?.getString(RETAURANT_TITLE_KEY)
        )
    }

    override fun getViewBinding(): FragmentMenuListBinding = FragmentMenuListBinding.inflate(layoutInflater)

    override fun observeData() = viewModel.reviewStateLiveData.observe(viewLifecycleOwner){
        when(it){
            is RestaurantReviewState.Success ->{
                handleSuccess(it)
            }
        }
    }
    private fun handleSuccess(state: RestaurantReviewState.Success){
        Toast.makeText(requireContext(), state.reviewList.toString(), Toast.LENGTH_SHORT).show()
    }

    companion object{

        const val RETAURANT_TITLE_KEY ="restaurantTitle"

        fun newInstance(restaurantTitle: String): RestaurantReviewListFragment{
            val bundle = bundleOf(
                RETAURANT_TITLE_KEY to restaurantTitle,
            )
            return RestaurantReviewListFragment().apply {
                arguments = bundle
            }
        }
    }
}