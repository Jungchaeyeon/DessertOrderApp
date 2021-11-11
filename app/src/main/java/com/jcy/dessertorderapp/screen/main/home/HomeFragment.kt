package com.jcy.dessertorderapp.screen.main.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.google.android.material.tabs.TabLayoutMediator
import com.jcy.dessertorderapp.R
import com.jcy.dessertorderapp.data.entity.LocationLatLngEntity
import com.jcy.dessertorderapp.databinding.FragmentHomeBinding
import com.jcy.dessertorderapp.screen.base.BaseFragment
import com.jcy.dessertorderapp.screen.main.restaurant.RestaurantCategory
import com.jcy.dessertorderapp.screen.main.restaurant.RestaurantListFragment
import com.jcy.dessertorderapp.widget.adapter.RestaurantListFragmentPagerAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeViewModel,FragmentHomeBinding>() {

    override val viewModel by viewModel<HomeViewModel>()

    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    private lateinit var viewPagerAdapter: RestaurantListFragmentPagerAdapter

    private lateinit var locationManager: LocationManager

    private lateinit var myLocationListener: MyLocationlistener

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

    override fun initViews() {
        super.initViews()
    }
    private fun initViewPager(locationLatLngEntity: LocationLatLngEntity) = with(binding){
        val restaurantCategories = RestaurantCategory.values()

        if(::viewPagerAdapter.isInitialized.not()){
            val restaurantListFragmentList = restaurantCategories.map{
                RestaurantListFragment.newInstance(it)
            }
            viewPagerAdapter = RestaurantListFragmentPagerAdapter(
                this@HomeFragment,
                restaurantListFragmentList
            )
        viewPager.adapter  = viewPagerAdapter
        }
        viewPager.offscreenPageLimit = restaurantCategories.size
        TabLayoutMediator(tabLayout, viewPager){ tab, position ->
            tab.setText(restaurantCategories[position].categoryNameId)
        }.attach()
    }

    override fun observeData() = viewModel.homeStateLiveData.observe(viewLifecycleOwner){
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