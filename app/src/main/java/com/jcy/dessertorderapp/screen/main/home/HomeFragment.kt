package com.jcy.dessertorderapp.screen.main.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.jcy.dessertorderapp.R
import com.jcy.dessertorderapp.data.entity.LocationLatLngEntity
import com.jcy.dessertorderapp.data.entity.MapSearchInfoEntity
import com.jcy.dessertorderapp.databinding.FragmentHomeBinding
import com.jcy.dessertorderapp.screen.MainActivity
import com.jcy.dessertorderapp.screen.MainTabMenu
import com.jcy.dessertorderapp.screen.base.BaseFragment
import com.jcy.dessertorderapp.screen.main.order.OrderMenuListActivity
import com.jcy.dessertorderapp.screen.main.restaurant.RestaurantCategory
import com.jcy.dessertorderapp.screen.main.restaurant.RestaurantListFragment
import com.jcy.dessertorderapp.screen.main.restaurant.RestaurantOrder
import com.jcy.dessertorderapp.screen.mylocation.MyLocationActivity
import com.jcy.dessertorderapp.widget.adapter.RestaurantListFragmentPagerAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeViewModel,FragmentHomeBinding>() {

    override val viewModel by viewModel<HomeViewModel>()

    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    private lateinit var viewPagerAdapter: RestaurantListFragmentPagerAdapter

    private lateinit var locationManager: LocationManager

    private lateinit var myLocationListener: MyLocationlistener

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val changeLocationLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
      if(result.resultCode == Activity.RESULT_OK){
          result.data?.getParcelableExtra<MapSearchInfoEntity>(
              HomeViewModel.MY_LOCATION_KEY
          )?.let { myLocationInfo ->
              viewModel.loadReverseGeoInfomation(myLocationInfo.locationLatLngEntity)
          }
      }
    }

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permissions->
            val responsePermissions = permissions.entries.filter {
                (it.key == Manifest.permission.ACCESS_FINE_LOCATION && it.value)
                        || ( it.key == Manifest.permission.ACCESS_COARSE_LOCATION && it.value)
            }
            if(responsePermissions.filter { it.value == true }.size == locationPermissions.size){
                //권한 성공적으로 받아옴
                setMyLocationListener()
            }else{
                //권한을 요청해야함
                with(binding.locationTitle){
                    setText(R.string.plz_setup_your_location_permission)
                    setOnClickListener {
                        getMyLocation()
                    }
                }
                Toast.makeText(requireContext(), getString(R.string.cannot_assigned_permission), Toast.LENGTH_SHORT).show()
            }
        }

    override fun initViews() =with(binding){
        locationTitle.setOnClickListener{
            viewModel.getMapSearchInfo()?.let{ mapInfo ->
                changeLocationLauncher.launch(
                    MyLocationActivity.newIntent(
                        requireContext(), mapInfo
                    )
                )
            }
        }
        orderChipGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.chipDefault ->{
                    chipInitialize.isGone = true
                    changeRestaurantOrder(RestaurantOrder.DEFAULT)
                }
                R.id.chipInitialize ->{
                    chipDefault.isChecked = true
                }
                R.id.chipLowTipDeilvery ->{
                    chipInitialize.isVisible = true
                    changeRestaurantOrder(RestaurantOrder.LOW_DELIVERY_TIP)
                }
                R.id.chipFastDelivery ->{
                    chipInitialize.isVisible = true
                    changeRestaurantOrder(RestaurantOrder.FAST_DELIVERY)
                }
                R.id.chipTopRate ->{
                    chipInitialize.isVisible = true
                    changeRestaurantOrder(RestaurantOrder.TOP_RATE)
                }
            }
        }
    }
    private fun changeRestaurantOrder(order: RestaurantOrder){
        viewPagerAdapter.fragmentList.forEach {
            it.viewModel.setRestaurantOrder(order)
        }
    }
    private fun initViewPager(locationLatLngEntity: LocationLatLngEntity) = with(binding){
        val restaurantCategories = RestaurantCategory.values()

        if(::viewPagerAdapter.isInitialized.not()){
            orderChipGroup.isVisible = true
            val restaurantListFragmentList = restaurantCategories.map{
                RestaurantListFragment.newInstance(it, locationLatLngEntity)
            }
            viewPagerAdapter = RestaurantListFragmentPagerAdapter(
                this@HomeFragment,
                restaurantListFragmentList,
                locationLatLngEntity
            )
        viewPager.adapter  = viewPagerAdapter
        viewPager.offscreenPageLimit = restaurantCategories.size
        TabLayoutMediator(tabLayout, viewPager){ tab, position ->
            tab.setText(restaurantCategories[position].categoryNameId)
        }.attach()
        }
        if(locationLatLngEntity != viewPagerAdapter.locationLatLngEntity){
            viewPagerAdapter.locationLatLngEntity = locationLatLngEntity
            viewPagerAdapter.fragmentList.forEach{
                it.viewModel.setLocationLatLng(locationLatLngEntity)
            }
        }
    }

    override fun observeData(){
        viewModel.homeStateLiveData.observe(viewLifecycleOwner){
            when(it){
                is HomeState.Unititialized ->{ //초기화 되어있지 않으면
                    getMyLocation()
                }
                is HomeState.Loading ->{
                    binding.locationLoading.isVisible = true
                    binding.locationTitle.text = getString(R.string.loading)
                }
                is HomeState.Success ->{
                    binding.locationLoading.isGone = true
                    binding.locationTitle.text = it.mapSearchInfoEntity.fullAddress
                    binding.tabLayout.isVisible = true
                    binding.filterScrollView.isVisible = true
                    binding.viewPager.isVisible = true
                    initViewPager(it.mapSearchInfoEntity.locationLatLngEntity)
                    if(it.isLocationSame.not()){
                        Toast.makeText(requireContext(), getString(R.string.please_set_your_current_location), Toast.LENGTH_SHORT).show()
                    }
                }
                is HomeState.Error ->{
                    binding.locationLoading.isGone = true
                    binding.locationTitle.text ="위치정보 없음"
                    binding.locationTitle.setOnClickListener {
                        getMyLocation()
                    }
                    Toast.makeText(requireContext(), it.messageId, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.foodMenuBasketLiveData.observe(this){
            if(it.isNotEmpty()){
                binding.basketBtnContainer.isVisible = true
                binding.basketCountTv.text = getString(R.string.basket_count, it.size)
                binding.basketBtn.setOnClickListener{
                    if(firebaseAuth.currentUser == null){
                        alertLoginNeed {
                            (requireActivity() as MainActivity).goToTab(MainTabMenu.MY)
                        }
                    }else{
                        startActivity(
                            OrderMenuListActivity.newIntent(requireContext())
                        )
                    }
                }
            }else{
                binding.basketBtnContainer.isGone = true
                binding.basketBtn.setOnClickListener(null)
            }
        }
    }
    private fun alertLoginNeed(afterAction: () -> Unit){
        AlertDialog.Builder(requireContext())
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

    private fun getMyLocation(){
        if(::locationManager.isInitialized.not()){
            locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }
        val isGpsUnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if(isGpsUnabled){ //gps가 켜져있으면
            locationPermissionLauncher.launch(locationPermissions)
        }
    }

    @SuppressLint("MissingPermission")
    private fun setMyLocationListener() {
        val minTime = 1500L
        val minDistance = 100f
        if(::myLocationListener.isInitialized.not()){
            myLocationListener = MyLocationlistener()
        }
        with(locationManager){
            requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime, minDistance, myLocationListener
            )
            requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                minTime, minDistance, myLocationListener
            )
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkMyBasket()
    }
    companion object{
        val locationPermissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        fun newInstance() = HomeFragment()

        const val TAG = "HomeFragment"
    }
    private fun removeLocationListener(){
        if(::locationManager.isInitialized && ::myLocationListener.isInitialized){
            locationManager.removeUpdates(myLocationListener)
        }
    }
    inner class MyLocationlistener: LocationListener{
        override fun onLocationChanged(location: Location) {
//            binding.locationTitle.text = "${location.latitude}, ${location.longitude}"
            viewModel.loadReverseGeoInfomation(
                LocationLatLngEntity(
                    location.latitude,
                    location.longitude
                )
            )
            removeLocationListener()
        }
    }
}