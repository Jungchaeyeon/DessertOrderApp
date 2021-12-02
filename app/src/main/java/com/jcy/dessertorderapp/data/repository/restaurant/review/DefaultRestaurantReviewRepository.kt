package com.jcy.dessertorderapp.data.repository.restaurant.review

import com.google.firebase.firestore.FirebaseFirestore
import com.jcy.dessertorderapp.data.entity.ReviewEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class DefaultRestaurantReviewRepository(
    private val ioDispatcher: CoroutineDispatcher,
    private val firestore: FirebaseFirestore
) : RestaurantReviewRepository{

    override suspend fun getReviews(restaurantTitle: String) :Result = withContext(ioDispatcher) {
        return@withContext try{
            val snapshot = firestore
                .collection("review")
                .whereEqualTo("restaurantTitle", restaurantTitle)
                .get()
                .await()

            Result.Success(snapshot.documents.map{
                ReviewEntity(
                    userId = it.get("userId") as String,
                    title = it.get("title") as String,
                    createAt = it.get("createAt") as Long,
                    content = it.get("content") as String,
                    rating = (it.get("rating") as Double).toFloat(),
                    imageUrlList = it.get("imageUrlList") as List<String>,
                    orderId = it.get("orderId") as String,
                    restaurantTitle= it.get("restaurantTitle") as String,
            )
            })
        } catch (e: Exception){
            e.printStackTrace()
            Result.Error(e)
        }

//        return@withContext (0..10).map{
//            RestaurantReviewEntity(
//                id= 0,
//                title = "제목 $it",
//                description = "내용 $it",
//                grade= (1 until 5).random().toFloat(),
//            )
//        }
    }
    sealed class Result{
        data class Success<T>(
            val data: T? = null
        ): Result()

        data class Error(
            val e: Throwable
        ): Result()
    }
}