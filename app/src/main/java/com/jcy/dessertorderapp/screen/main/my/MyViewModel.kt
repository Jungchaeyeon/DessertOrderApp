package com.jcy.dessertorderapp.screen.main.my

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.jcy.dessertorderapp.data.entity.OrderEntity
import com.jcy.dessertorderapp.data.preference.AppPreferenceManager
import com.jcy.dessertorderapp.data.repository.order.DefaultOrderRepository
import com.jcy.dessertorderapp.data.repository.order.OrderRepository
import com.jcy.dessertorderapp.data.repository.user.UserRepository
import com.jcy.dessertorderapp.screen.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyViewModel(
    private val appPreferenceManager: AppPreferenceManager,
    private val userRepository: UserRepository,
    private val orderRepository: OrderRepository
): BaseViewModel() {

    val myStateLiveData = MutableLiveData<MyState>(MyState.Uninitialized)

    override fun fetchData(): Job = viewModelScope.launch{
        myStateLiveData.value = MyState.Loading
        appPreferenceManager.getIdToken()?.let {
            myStateLiveData.value = MyState.Login(it)
        } ?: kotlin.run {
            myStateLiveData.value = MyState.Success.NotRegistered
        }
    }
    fun saveToken(idToken: String) = viewModelScope.launch{
        withContext(Dispatchers.IO){
            appPreferenceManager.putIdToken(idToken)
            fetchData()
        }
    }

    fun setUserInfo(firebaseUser: FirebaseUser?) = viewModelScope.launch{
        firebaseUser?.let { user ->
            when(val orderMenusResult = orderRepository.getAllOrderMenus(user.uid)){
                is DefaultOrderRepository.Result.Success<*> ->{
                    val orderList = orderMenusResult.data as List<OrderEntity>
                    myStateLiveData.value= MyState.Success.Registered(
                        userName = user.displayName ?: "익명",
                        profileImageUri =  user.photoUrl,
                        orderList = orderList
                    )
                }
            }

        } ?: kotlin.run {
            myStateLiveData.value = MyState.Success.NotRegistered
        }
    }

    fun signOut() = viewModelScope.launch{
        withContext(Dispatchers.IO){
            appPreferenceManager.removeIdToken()
        }
        userRepository.deleteAllUserLikedRestaurant()
        fetchData()
    }
}