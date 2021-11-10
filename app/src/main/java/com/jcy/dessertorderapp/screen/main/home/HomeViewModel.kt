package com.jcy.dessertorderapp.screen.main.home

import androidx.lifecycle.MutableLiveData
import com.jcy.dessertorderapp.screen.base.BaseViewModel

class HomeViewModel: BaseViewModel() {

    val homeStateLiveData = MutableLiveData<HomeState>(HomeState.Unititialized)
}