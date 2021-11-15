package com.jcy.dessertorderapp.di

import com.jcy.dessertorderapp.data.entity.LocationLatLngEntity
import com.jcy.dessertorderapp.data.entity.MapSearchInfoEntity
import com.jcy.dessertorderapp.data.repository.DefaultRestaurantRepository
import com.jcy.dessertorderapp.data.repository.RestaurantRepository
import com.jcy.dessertorderapp.data.repository.map.DefaultMapRepository
import com.jcy.dessertorderapp.data.repository.map.MapRepository
import com.jcy.dessertorderapp.data.repository.user.DefaultUserRepository
import com.jcy.dessertorderapp.data.repository.user.UserRepository
import com.jcy.dessertorderapp.screen.main.home.HomeViewModel
import com.jcy.dessertorderapp.screen.main.my.MyViewModel
import com.jcy.dessertorderapp.screen.main.restaurant.RestaurantCategory
import com.jcy.dessertorderapp.screen.main.restaurant.RestaurantListViewModel
import com.jcy.dessertorderapp.screen.mylocation.MyLocationViewModel
import com.jcy.dessertorderapp.util.provider.DefaultResourceProvider
import com.jcy.dessertorderapp.util.provider.ResourceProvider
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel{ HomeViewModel(get(),get()) }
    viewModel { MyViewModel() }
    viewModel { (restaurantCategory: RestaurantCategory, locationLatLng: LocationLatLngEntity) ->
        RestaurantListViewModel(restaurantCategory, locationLatLng, get()) }
    viewModel { (mapSearchInfoEntity : MapSearchInfoEntity) -> MyLocationViewModel(mapSearchInfoEntity,get(),get())}

    single<MapRepository> { DefaultMapRepository(get(),get()) }
    single<RestaurantRepository> { DefaultRestaurantRepository(get(),get(),get())}
    single<UserRepository> { DefaultUserRepository(get(),get())}

    single { provideGsonConverterFactory() }
    single { buildOkHttpClient() }

    single { provideMapRetrofit(get(),get())}
    single { provideMapApiService(get()) }
    single { provideDB(androidApplication()) }
    single { provideLocationDao(get()) }

    single<ResourceProvider>{ DefaultResourceProvider(androidApplication())}

    single { Dispatchers.IO }
    single { Dispatchers.Main }
}