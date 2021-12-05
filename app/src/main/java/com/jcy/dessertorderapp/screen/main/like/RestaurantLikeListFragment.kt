package com.jcy.dessertorderapp.screen.main.like

import android.util.Log
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.jcy.dessertorderapp.databinding.FragmentRestaurantLikeListBinding
import com.jcy.dessertorderapp.model.restaurant.RestaurantModel
import com.jcy.dessertorderapp.screen.base.BaseFragment
import com.jcy.dessertorderapp.screen.main.restaurant.detail.RestaurantDetailActivity
import com.jcy.dessertorderapp.util.provider.ResourceProvider
import com.jcy.dessertorderapp.widget.adapter.ModelRecyclerAdapter
import com.jcy.dessertorderapp.widget.adapter.listener.restaurant.RestaurantLikeListListener
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class RestaurantLikeListFragment : BaseFragment<RestaurantLikeListViewModel,FragmentRestaurantLikeListBinding>() {

    override val viewModel by viewModel<RestaurantLikeListViewModel>()

    override fun getViewBinding(): FragmentRestaurantLikeListBinding = FragmentRestaurantLikeListBinding.inflate(layoutInflater)

    private val resourceProvider by inject<ResourceProvider>()

    private val adapter by lazy{
        ModelRecyclerAdapter<RestaurantModel, RestaurantLikeListViewModel>(
            listOf(),
            viewModel,
            resourceProvider,
            adapterListener = object : RestaurantLikeListListener {
                override fun onDislikeItem(model: RestaurantModel) {
                    viewModel.dislikeRestaurant(model.toEntity())
                }

                override fun onClickItem(model: RestaurantModel) {
                    startActivity(
                        RestaurantDetailActivity.newIntent(requireContext(), model.toEntity())
                    )
                }
            }
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchData()
    }

    override fun initViews() {
        binding.recyclerView.adapter = adapter
    }

    override fun observeData() = viewModel.restaurantListLiveData.observe(viewLifecycleOwner){
        checkListEmpty(it)
    }
    private fun checkListEmpty(restaurantList: List<RestaurantModel>){
        val isEmpty = restaurantList.isEmpty()
        binding.recyclerView.isGone = isEmpty
        binding.emptyResultTv.isVisible = isEmpty
        if(isEmpty.not()){
            adapter.submitList(restaurantList)
        }
    }
    companion object{
        fun newInstance() = RestaurantLikeListFragment()

        const val TAG = "restaurantLikeListFragment"
    }

}