package com.jcy.dessertorderapp.screen.mylocation

import androidx.annotation.StringRes
import com.jcy.dessertorderapp.data.entity.MapSearchInfoEntity
import com.jcy.dessertorderapp.screen.main.home.HomeState

sealed class MyLocationState{
    object Unitialized: MyLocationState()

    object Loading: MyLocationState()

    data class Success(
        val mapSearchInfoEntity: MapSearchInfoEntity
    ): MyLocationState()

    data class Confirm(
        val mapSearchInfoEntity: MapSearchInfoEntity
    ): MyLocationState()

    data class Error(
        @StringRes val messageId: Int
    ): MyLocationState()
}
