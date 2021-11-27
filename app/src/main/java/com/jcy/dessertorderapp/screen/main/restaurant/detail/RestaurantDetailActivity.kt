package com.jcy.dessertorderapp.screen.main.restaurant.detail

import android.app.AlertDialog
import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.jcy.dessertorderapp.R
import com.jcy.dessertorderapp.data.entity.RestaurantEntity
import com.jcy.dessertorderapp.data.entity.RestaurantFoodEntity
import com.jcy.dessertorderapp.databinding.ActivityRestaurantDetailBinding
import com.jcy.dessertorderapp.ext.fromDpToPx
import com.jcy.dessertorderapp.ext.load
import com.jcy.dessertorderapp.screen.MainTabMenu
import com.jcy.dessertorderapp.screen.base.BaseActivity
import com.jcy.dessertorderapp.screen.main.restaurant.RestaurantListFragment
import com.jcy.dessertorderapp.screen.main.restaurant.detail.menu.RestaurantMenuListFragment
import com.jcy.dessertorderapp.screen.main.restaurant.detail.review.RestaurantReviewListFragment
import com.jcy.dessertorderapp.util.event.MenuChangeEventBus
import com.jcy.dessertorderapp.widget.adapter.RestaurantDetailListFragmentPagerAdapter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.lang.Math.abs

class RestaurantDetailActivity : BaseActivity<RestaurantDetailViewModel, ActivityRestaurantDetailBinding>() {

    override val viewModel by viewModel<RestaurantDetailViewModel>{
        parametersOf(
            intent.getParcelableExtra<RestaurantEntity>(RestaurantListFragment.RESTAURANT_KEY)
        )
    }

    override fun initViews() {
        initAppBar()
    }
    private val menuChangeEventBus by inject<MenuChangeEventBus>()

    private lateinit var viewPagerAdapter : RestaurantDetailListFragmentPagerAdapter

    override fun getViewBinding(): ActivityRestaurantDetailBinding = ActivityRestaurantDetailBinding.inflate(layoutInflater)

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    private fun initAppBar() = with(binding){
        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener{ appBarLayout, verticalOffset ->
            val topPadding = 300f.fromDpToPx().toFloat()
            val realAlphaScrollHeight = appBarLayout.measuredHeight - appBarLayout.totalScrollRange
            val abstractOffset = abs(verticalOffset)

            val realAlphaVerticalOffset: Float = if (abstractOffset - topPadding < 0) 0f else abstractOffset - topPadding

            if (abstractOffset < topPadding) {
                restaurantTitleTv.alpha = 0f
                return@OnOffsetChangedListener
            }
            val percentage = realAlphaVerticalOffset / realAlphaScrollHeight
            restaurantTitleTv.alpha = 1 - (if (1 - percentage * 2 < 0) 0f else 1 - percentage * 2)
        })
        toolbar.setNavigationOnClickListener {
            finish()
        }
        callButton.setOnClickListener {
            viewModel.getRestaurantPhoneNumber()?.let{ telNumber ->
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$telNumber"))
                startActivity(intent)
            }
        }
        likeButton.setOnClickListener {
            viewModel.toggleLikedRestaurant()
        }
        shareButton.setOnClickListener {
            viewModel.getRestaurantInfo()?.let { restaurantInfo ->
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = MIMETYPE_TEXT_PLAIN
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "맛있는 음식점 : ${restaurantInfo.restaurantTitle}" +
                                "\n평점 : ${restaurantInfo.grade}" +
                                "\n연락처 : ${restaurantInfo.restaurantTelNumber}"
                    )
                    Intent.createChooser(this, "친구에게 공유하기")
                }
                startActivity(intent)
            }
        }
    }

    override fun observeData() = viewModel.restaurantDetailStateLiveData.observe(this){
        when(it){
            is RestaurantDetailState.Loading ->{
                handleLoading()
            }
            is RestaurantDetailState.Success ->{
                handleSuccess(it)
            }
        }
    }
    private fun handleLoading() = with(binding) {
        progressBar.isVisible = true
    }

    private fun handleSuccess(state: RestaurantDetailState.Success) = with(binding) {
        progressBar.isGone = true

        //ok - Log.e("restaurantFoodList",state.restaurantFoodList.toString())

        val restaurantEntity = state.restaurantEntity

        callButton.isGone = restaurantEntity.restaurantTelNumber == null

        restaurantTitleTv.text = restaurantEntity.restaurantTitle
        restaurantImage.load(restaurantEntity.restaurantImageUrl)
        restaurantMainTitleText.text = restaurantEntity.restaurantTitle
        ratingBar.rating = restaurantEntity.grade
        ratingTv.text = restaurantEntity.grade.toString()
        deliveryTimeText.text =
            getString(R.string.delivery_expected_time, restaurantEntity.deliveryTimeRange.first, restaurantEntity.deliveryTimeRange.second)
        deliveryTipText.text =
            getString(R.string.delivery_tip_range, restaurantEntity.deliveryTipRange.first, restaurantEntity.deliveryTipRange.second)

        likeText.setCompoundDrawablesWithIntrinsicBounds(
            ContextCompat.getDrawable(this@RestaurantDetailActivity, if (state.isLiked == true) {
                R.drawable.ic_favorite_enabled
            } else {
                R.drawable.ic_favorite
            }),
            null, null, null
        )
        if(::viewPagerAdapter.isInitialized.not()){
            initViewPager(state.restaurantEntity.restaurantInfoId,state.restaurantEntity.restaurantTitle,state.restaurantFoodList ?: listOf())
        }
        notifyBasketCount(state.foodMenuListInBasket)

        val (isClearNeed, afterAction) = state.isClearNeedInBasketAndAction
        if(isClearNeed){
            alertClearNeedInBasket(afterAction)
        }
    }

    private fun notifyBasketCount(foodMenuListInBasket: List<RestaurantFoodEntity>?) = with(binding){
        basketCountTv.text = if(foodMenuListInBasket.isNullOrEmpty()){
            "0"
        }else{
            getString(R.string.basket_count, foodMenuListInBasket.size)
        }
        basketBtn.setOnClickListener{
            if(firebaseAuth.currentUser == null){
                alertLoginNeed{
                    lifecycleScope.launch{
                        menuChangeEventBus.changeMenu(MainTabMenu.MY)
                        finish()
                    }
                }
            }else{

            }
        }
    }

    private fun alertLoginNeed(afterAction: () -> Unit){
        AlertDialog.Builder(this)
            .setTitle("로그인이 필요합니다.")
            .setMessage("주문하려면 로그인이 필요합니다. My탭으로 이동하시겠습니까?")
            .setPositiveButton("이동"){dialog,_ ->
                afterAction()
                dialog.dismiss()
            }
            .setNegativeButton("취소"){dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun initViewPager(restaurantInfoId: Long, restaurantTitle: String,restaurantFoodList: List<RestaurantFoodEntity>?){
        viewPagerAdapter = RestaurantDetailListFragmentPagerAdapter(
            this,
            listOf(
                RestaurantMenuListFragment.newInstance(restaurantInfoId, ArrayList(restaurantFoodList ?: listOf())),
                RestaurantReviewListFragment.newInstance(restaurantTitle)
            )
        )
        binding.menuAndReviewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.menuAndReviewTabLayout, binding.menuAndReviewPager){ tab, position ->
            tab.setText(RestaurantCategoryDetail.values()[position].categoryNameId)
        }.attach()
    }

    private fun alertClearNeedInBasket(afterAction: () -> Unit){
        AlertDialog.Builder(this)
            .setTitle("장바구니에는 같은 가게의 메뉴만 담을 수 있습니다")
            .setMessage("선택하신 메뉴를 장바구니에 담을 경우 이전에 담은 메뉴가 삭제됩니다.")
            .setPositiveButton("담기"){dialog,_ ->
                viewModel.notifyClearBasket() //Success데이터 위주 clear
                afterAction()
                dialog.dismiss()
            }
            .setNegativeButton("취소"){dialog,_ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    companion object{
        fun newIntent(context: Context, restaurantEntity: RestaurantEntity) = Intent(context,RestaurantDetailActivity::class.java).apply{
            putExtra(RestaurantListFragment.RESTAURANT_KEY, restaurantEntity)
        }
    }
}