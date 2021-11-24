package com.jcy.dessertorderapp.screen.main.restaurant.detail.review

import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import com.jcy.dessertorderapp.data.entity.RestaurantFoodEntity
import com.jcy.dessertorderapp.databinding.FragmentMenuListBinding
import com.jcy.dessertorderapp.model.restaurant.food.FoodModel
import com.jcy.dessertorderapp.model.restaurant.review.RestaurantReviewModel
import com.jcy.dessertorderapp.screen.base.BaseFragment
import com.jcy.dessertorderapp.screen.main.restaurant.detail.menu.RestaurantMenuListViewModel
import com.jcy.dessertorderapp.util.provider.ResourceProvider
import com.jcy.dessertorderapp.widget.adapter.ModelRecyclerAdapter
import com.jcy.dessertorderapp.widget.adapter.listener.AdapterListener
import com.jcy.dessertorderapp.widget.adapter.listener.restaurant.FoodMenuListListener
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RestaurantReviewListFragment :
    BaseFragment<RestaurantReviewListViewModel, FragmentMenuListBinding>() {
    override val viewModel by viewModel<RestaurantReviewListViewModel>{
        parametersOf(
            arguments?.getString(RETAURANT_TITLE_KEY)
        )
    }

    private val resourceProvider by inject<ResourceProvider>()

    private val adapter by lazy {
        ModelRecyclerAdapter<RestaurantReviewModel, RestaurantReviewListViewModel>(
            listOf(),
            viewModel,
            resourceProvider,
            adapterListener = object : AdapterListener{}
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

    override fun initViews() {
        binding.recyclerView.adapter = adapter
    }
    private fun handleSuccess(state: RestaurantReviewState.Success){
        adapter.submitList(state.reviewList)
        Log.e("state", state.reviewList.toString())
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