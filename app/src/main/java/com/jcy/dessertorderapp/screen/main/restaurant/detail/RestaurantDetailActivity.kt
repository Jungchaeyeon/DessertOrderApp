package com.jcy.dessertorderapp.screen.main.restaurant.detail

import android.content.Context
import android.content.Intent
import com.jcy.dessertorderapp.data.entity.RestaurantEntity
import com.jcy.dessertorderapp.databinding.ActivityRestaurantDetailBinding
import com.jcy.dessertorderapp.screen.base.BaseActivity
import com.jcy.dessertorderapp.screen.main.restaurant.RestaurantListFragment
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RestaurantDetailActivity : BaseActivity<RestaurantDetailViewModel, ActivityRestaurantDetailBinding>() {

    override val viewModel by viewModel<RestaurantDetailViewModel>{
        parametersOf(
            intent.getParcelableExtra<RestaurantEntity>(RestaurantListFragment.RESTAURANT_KEY)
        )
    }

    override fun getViewBinding(): ActivityRestaurantDetailBinding = ActivityRestaurantDetailBinding.inflate(layoutInflater)

    override fun observeData() = viewModel.restaurantDetailStateLiveData.observe(this){
        when(it){

        }
    }

    companion object{
        fun newIntent(context: Context, restaurantEntity: RestaurantEntity) = Intent(context,RestaurantDetailActivity::class.java).apply{
            putExtra(RestaurantListFragment.RESTAURANT_KEY, restaurantEntity)
        }
    }
}