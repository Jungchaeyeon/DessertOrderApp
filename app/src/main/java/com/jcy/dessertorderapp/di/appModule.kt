package com.jcy.dessertorderapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jcy.dessertorderapp.data.entity.LocationLatLngEntity
import com.jcy.dessertorderapp.data.entity.MapSearchInfoEntity
import com.jcy.dessertorderapp.data.entity.RestaurantEntity
import com.jcy.dessertorderapp.data.entity.RestaurantFoodEntity
import com.jcy.dessertorderapp.data.preference.AppPreferenceManager
import com.jcy.dessertorderapp.data.repository.DefaultRestaurantRepository
import com.jcy.dessertorderapp.data.repository.RestaurantRepository
import com.jcy.dessertorderapp.data.repository.map.DefaultMapRepository
import com.jcy.dessertorderapp.data.repository.map.MapRepository
import com.jcy.dessertorderapp.data.repository.restaurant.food.DefaultRestaurantFoodRepository
import com.jcy.dessertorderapp.data.repository.restaurant.food.RestaurantFoodRepository
import com.jcy.dessertorderapp.data.repository.restaurant.review.DefaultRestaurantReviewRepository
import com.jcy.dessertorderapp.data.repository.restaurant.review.RestaurantReviewRepository
import com.jcy.dessertorderapp.data.repository.user.DefaultUserRepository
import com.jcy.dessertorderapp.data.repository.user.UserRepository
import com.jcy.dessertorderapp.screen.main.home.HomeViewModel
import com.jcy.dessertorderapp.screen.main.like.RestaurantLikeListViewModel
import com.jcy.dessertorderapp.screen.main.my.MyViewModel
import com.jcy.dessertorderapp.data.repository.order.DefaultOrderRepository
import com.jcy.dessertorderapp.screen.main.order.OrderMenuListViewModel
import com.jcy.dessertorderapp.data.repository.order.OrderRepository
import com.jcy.dessertorderapp.screen.main.restaurant.RestaurantCategory
import com.jcy.dessertorderapp.screen.main.restaurant.RestaurantListViewModel
import com.jcy.dessertorderapp.screen.main.restaurant.detail.RestaurantDetailViewModel
import com.jcy.dessertorderapp.screen.main.restaurant.detail.menu.RestaurantMenuListViewModel
import com.jcy.dessertorderapp.screen.main.restaurant.detail.review.RestaurantReviewListViewModel
import com.jcy.dessertorderapp.screen.mylocation.MyLocationViewModel
import com.jcy.dessertorderapp.util.event.MenuChangeEventBus
import com.jcy.dessertorderapp.util.provider.DefaultResourceProvider
import com.jcy.dessertorderapp.util.provider.ResourceProvider
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    viewModel{ HomeViewModel(get(),get(),get()) }
    viewModel { MyViewModel(get(),get(),get()) }
    viewModel { (restaurantCategory: RestaurantCategory, locationLatLng: LocationLatLngEntity) ->
        RestaurantListViewModel(restaurantCategory, locationLatLng, get()) }
    viewModel { (mapSearchInfoEntity : MapSearchInfoEntity) -> MyLocationViewModel(mapSearchInfoEntity,get(),get())}
    viewModel { (restaurantEntity: RestaurantEntity) -> RestaurantDetailViewModel(restaurantEntity,get(),get()) }
    viewModel { (restaurantId: Long, restaurantFoodList: List<RestaurantFoodEntity>)->
        RestaurantMenuListViewModel(restaurantId, restaurantFoodList,get()) }
    viewModel { (repositoryTitle:String) -> RestaurantReviewListViewModel(repositoryTitle,get()) }
    viewModel { RestaurantLikeListViewModel(get())}
    viewModel { OrderMenuListViewModel(get(),get()) }

    single<MapRepository> { DefaultMapRepository(get(),get()) }
    single<RestaurantRepository> { DefaultRestaurantRepository(get(),get(),get())}
    single<UserRepository> { DefaultUserRepository(get(),get(),get())}
    single<RestaurantFoodRepository>{ DefaultRestaurantFoodRepository(get(),get(),get())}
    single<RestaurantReviewRepository> { DefaultRestaurantReviewRepository(get())}
    single<OrderRepository>{DefaultOrderRepository(get(),get()) }

    single { provideGsonConverterFactory() }
    single { buildOkHttpClient() }

    single { provideMapApiService(get(qualifier = named("map"))) }
    single(named("map")) { provideMapRetrofit(get(),get())}

    single { provideFoodApiService(get(qualifier = named("food"))) }
    single(named("food")) { provideFoodRetrofit(get(),get()) }

    single { provideDB(androidApplication()) }
    single { provideLocationDao(get()) }
    single { provideRestaurantDao(get()) }
    single { provideFoodMenuBasketDao(get())}

    single<ResourceProvider>{ DefaultResourceProvider(androidApplication())}
    single { AppPreferenceManager(androidApplication()) }

    single { Dispatchers.IO }
    single { Dispatchers.Main }

    single{ MenuChangeEventBus() }

    single{ Firebase.firestore }
    single{ FirebaseAuth.getInstance() }
}