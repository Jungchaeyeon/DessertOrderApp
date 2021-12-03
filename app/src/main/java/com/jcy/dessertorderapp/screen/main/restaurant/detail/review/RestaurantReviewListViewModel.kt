package com.jcy.dessertorderapp.screen.main.restaurant.detail.review

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jcy.dessertorderapp.data.entity.ReviewEntity
import com.jcy.dessertorderapp.data.repository.restaurant.review.DefaultRestaurantReviewRepository
import com.jcy.dessertorderapp.data.repository.restaurant.review.RestaurantReviewRepository
import com.jcy.dessertorderapp.model.restaurant.review.RestaurantReviewModel
import com.jcy.dessertorderapp.screen.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RestaurantReviewListViewModel(
    private val restaurantTitle: String,
    private val restaurantReviewRepository: RestaurantReviewRepository
) : BaseViewModel() {

    val reviewStateLiveData = MutableLiveData<RestaurantReviewState>(RestaurantReviewState.Uninitialized)

    override fun fetchData(): Job = viewModelScope.launch{
        reviewStateLiveData.value = RestaurantReviewState.Loading
        val result = restaurantReviewRepository.getReviews(restaurantTitle)
        when(result){
            is DefaultRestaurantReviewRepository.Result.Success<*> ->{
                val reviews = result.data as List<ReviewEntity>
                reviewStateLiveData.value = RestaurantReviewState.Success(
                    reviews.map{
                        RestaurantReviewModel(
                            id = it.hashCode().toLong(),
                            title = it.title,
                            description = it.content,
                            grade = it.rating,
                            thumbnailImageUri = if(it.imageUrlList.isNullOrEmpty()){
                                null
                            }else{
                                Uri.parse(it.imageUrlList.first())
                            }
                        )
                    }
                )
            }
            else -> Unit
        }

    }
}