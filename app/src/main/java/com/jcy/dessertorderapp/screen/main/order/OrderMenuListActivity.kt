package com.jcy.dessertorderapp.screen.main.order

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.jcy.dessertorderapp.databinding.ActivityOrderMenuListBinding
import com.jcy.dessertorderapp.model.restaurant.RestaurantModel
import com.jcy.dessertorderapp.model.restaurant.food.FoodModel
import com.jcy.dessertorderapp.screen.base.BaseActivity
import com.jcy.dessertorderapp.screen.main.restaurant.RestaurantListViewModel
import com.jcy.dessertorderapp.screen.main.restaurant.detail.RestaurantDetailActivity
import com.jcy.dessertorderapp.util.provider.ResourceProvider
import com.jcy.dessertorderapp.widget.adapter.ModelRecyclerAdapter
import com.jcy.dessertorderapp.widget.adapter.listener.order.OrderMenuListListener
import com.jcy.dessertorderapp.widget.adapter.listener.restaurant.RestaurantListListener
import org.koin.android.ext.android.inject

class OrderMenuListActivity : BaseActivity<OrderMenuListViewModel,ActivityOrderMenuListBinding>() {
    override val viewModel by inject<OrderMenuListViewModel>()

    override fun getViewBinding(): ActivityOrderMenuListBinding = ActivityOrderMenuListBinding.inflate(layoutInflater)

    private val resourceProvider by inject<ResourceProvider>()

    private val adapter by lazy {
        ModelRecyclerAdapter<FoodModel, OrderMenuListViewModel>(listOf(), viewModel, resourceProvider, adapterListener = object : OrderMenuListListener {
            override fun onRemoveItem(model: FoodModel) {
                viewModel.removeOrderMenu(model)
            }

        })
    }

    override fun initViews() = with(binding){
        recyclerVIew.adapter = adapter
        toolbar.setNavigationOnClickListener { finish() }
        confirmButton.setOnClickListener {
            viewModel.orderMenu()
        }
        orderClearButton.setOnClickListener{
            viewModel.clearOrderMenu()
        }
    }

    override fun observeData() = viewModel.orderMenuStateLiveData.observe(this){
        when(it){
            is OrderMenuState.Loading ->{
                handleLoading()
            }
            is OrderMenuState.Success ->{
                handleSuccess(it)
            }
            is OrderMenuState.Order ->{

            }
            is OrderMenuState.Error ->{

            }
            else -> Unit
        }
    }
    private fun handleLoading() = with(binding){
        progressBar.isVisible = true
    }
    private fun handleSuccess(state: OrderMenuState.Success) = with(binding){
        progressBar.isGone = true
        adapter.submitList(state.restaurantFoodModelList)
        val menuOrderIsEmpty = state.restaurantFoodModelList.isNullOrEmpty()
        confirmButton.isEnabled = menuOrderIsEmpty.not()
        if(menuOrderIsEmpty){
            Toast.makeText(this@OrderMenuListActivity, "주문 메뉴가 없어 화면을 종료합니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
    companion object{
        fun newIntent(context: Context)= Intent(context, OrderMenuListActivity::class.java)
    }
}