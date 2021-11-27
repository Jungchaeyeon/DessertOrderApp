package com.jcy.dessertorderapp.screen.main.order

import com.jcy.dessertorderapp.databinding.ActivityOrderMenuListBinding
import com.jcy.dessertorderapp.screen.base.BaseActivity
import org.koin.android.ext.android.inject

class OrderMenuListActivity : BaseActivity<OrderMenuListViewModel,ActivityOrderMenuListBinding>() {
    override val viewModel by inject<OrderMenuListViewModel>()

    override fun getViewBinding(): ActivityOrderMenuListBinding = ActivityOrderMenuListBinding.inflate(layoutInflater)

    override fun observeData() {

    }
}