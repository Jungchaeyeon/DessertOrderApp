package com.jcy.dessertorderapp.screen.main.restaurant.detail.menu

import android.widget.Toast
import androidx.core.os.bundleOf
import com.jcy.dessertorderapp.data.entity.RestaurantFoodEntity
import com.jcy.dessertorderapp.databinding.FragmentMenuListBinding
import com.jcy.dessertorderapp.model.restaurant.RestaurantModel
import com.jcy.dessertorderapp.model.restaurant.food.FoodModel
import com.jcy.dessertorderapp.screen.base.BaseFragment
import com.jcy.dessertorderapp.screen.main.restaurant.detail.RestaurantDetailActivity
import com.jcy.dessertorderapp.screen.main.restaurant.detail.RestaurantDetailViewModel
import com.jcy.dessertorderapp.util.provider.ResourceProvider
import com.jcy.dessertorderapp.widget.adapter.ModelRecyclerAdapter
import com.jcy.dessertorderapp.widget.adapter.listener.AdapterListener
import com.jcy.dessertorderapp.widget.adapter.listener.restaurant.FoodMenuListListener
import com.jcy.dessertorderapp.widget.adapter.listener.restaurant.RestaurantListListener
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RestaurantMenuListFragment :
    BaseFragment<RestaurantMenuListViewModel, FragmentMenuListBinding>() {

    private val restaurantId by lazy { arguments?.getLong(RESTAURANT_ID_KEY,-1) }
    private val restaurantFoodList by lazy {
        arguments?.getParcelableArrayList<RestaurantFoodEntity>(FOOD_LIST_KEY)!!
    }
    override val viewModel by viewModel<RestaurantMenuListViewModel>{
        parametersOf(
            restaurantId,
            restaurantFoodList
        )
    }

    private val restaurantDetailViewModel by sharedViewModel<RestaurantDetailViewModel>()

    private val resourceProvider by inject<ResourceProvider>()

    private val adapter by lazy{
        ModelRecyclerAdapter<FoodModel, RestaurantMenuListViewModel>(
            listOf(),
            viewModel,
            resourceProvider,
            adapterListener = object : FoodMenuListListener{
                override fun onClickItem(model: FoodModel) {
                    viewModel.insertMenuInBasket(model)
                }
            }
        )
    }

    override fun initViews() = with(binding){
      recyclerView.adapter = adapter
    }

    override fun getViewBinding(): FragmentMenuListBinding = FragmentMenuListBinding.inflate(layoutInflater)

    override fun observeData() {
        viewModel.restaurantFoodListLiveData.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        viewModel.menuBasketLivedata.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), "??????????????? ???????????????. ?????? : ${it.title}", Toast.LENGTH_SHORT).show()
            restaurantDetailViewModel.notifyFoodMenuListInBasket(it)
        }
        viewModel.isClearNeedInBasketLiveData.observe(viewLifecycleOwner){ (isClearNeed, afterAction)->
            if(isClearNeed){
                restaurantDetailViewModel.notifyClearNeedAlertInBasket(isClearNeed, afterAction)
            }
        }
    }
    companion object{

        const val RESTAURANT_ID_KEY ="restaurantId"

        const val FOOD_LIST_KEY = "foodList"

        fun newInstance(restaurantId: Long, foodList: ArrayList<RestaurantFoodEntity>?): RestaurantMenuListFragment{
            val bundle = bundleOf(
                RESTAURANT_ID_KEY to restaurantId,
                FOOD_LIST_KEY to foodList
            )
            return RestaurantMenuListFragment().apply {
                arguments = bundle
            }
        }
    }
}