package com.jcy.dessertorderapp.di

import com.jcy.dessertorderapp.data.repository.DefaultRestaurantRepository
import com.jcy.dessertorderapp.data.repository.RestaurantRepository
import com.jcy.dessertorderapp.screen.main.home.HomeViewModel
import com.jcy.dessertorderapp.screen.main.my.MyViewModel
import com.jcy.dessertorderapp.screen.main.restaurant.RestaurantCategory
import com.jcy.dessertorderapp.screen.main.restaurant.RestaurantListViewModel
import com.jcy.dessertorderapp.util.provider.DefaultResourceProvider
import com.jcy.dessertorderapp.util.provider.ResourceProvider
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel{ HomeViewModel() }
    viewModel { MyViewModel() }
    viewModel { (restaurantCategory: RestaurantCategory) -> RestaurantListViewModel(restaurantCategory, get()) }

    single<RestaurantRepository> { DefaultRestaurantRepository(get(),get())}

    single { provideGsonConverterFactory() }
    single { buildOkHttpClient() }

    single { provideRetrofit(get(),get()) }

    single<ResourceProvider>{ DefaultResourceProvider(androidApplication())}
    single { Dispatchers.IO }
    single { Dispatchers.Main }
}