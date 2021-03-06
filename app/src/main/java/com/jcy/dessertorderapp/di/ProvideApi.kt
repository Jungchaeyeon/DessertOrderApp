package com.jcy.dessertorderapp.di

import com.jcy.dessertorderapp.data.network.FoodApiService
import com.jcy.dessertorderapp.data.network.MapApiService
import com.jcy.dessertorderapp.data.url.Url
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun provideFoodApiService(retrofit: Retrofit): FoodApiService{
    return retrofit.create(FoodApiService::class.java)
}

fun provideFoodRetrofit(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory
): Retrofit{
    return Retrofit.Builder()
        .baseUrl(Url.DESSERT_URL)
        .addConverterFactory(gsonConverterFactory)
        .client(okHttpClient)
        .build()
}

fun provideMapApiService(retrofit: Retrofit):  MapApiService{
    return retrofit.create(MapApiService::class.java)
}
fun provideMapRetrofit(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory
): Retrofit{
    return Retrofit.Builder()
        .baseUrl(Url.TMAP_URL)
        .addConverterFactory(gsonConverterFactory)
        .client(okHttpClient)
        .build()
}

fun provideGsonConverterFactory(): GsonConverterFactory{
    return GsonConverterFactory.create()
}
fun buildOkHttpClient(): OkHttpClient{
    val interceptor = HttpLoggingInterceptor()
    if(BuildConfig.DEBUG){
        interceptor.level = HttpLoggingInterceptor.Level.BODY
    }else{
        interceptor.level = HttpLoggingInterceptor.Level.NONE
    }
    return OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .build()
}