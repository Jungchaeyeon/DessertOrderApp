package com.jcy.dessertorderapp.di

import com.jcy.dessertorderapp.data.TestRestaurantRepository
import com.jcy.dessertorderapp.data.entity.LocationLatLngEntity
import com.jcy.dessertorderapp.data.repository.RestaurantRepository
import com.jcy.dessertorderapp.screen.main.restaurant.RestaurantCategory
import com.jcy.dessertorderapp.screen.main.restaurant.RestaurantListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appTestModule = module{
    viewModel { (restaurantCategory: RestaurantCategory, locationLatLng: LocationLatLngEntity) ->
        RestaurantListViewModel(restaurantCategory, locationLatLng, get()) }

    single<RestaurantRepository> { TestRestaurantRepository() }
}