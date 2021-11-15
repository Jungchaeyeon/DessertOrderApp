package com.jcy.dessertorderapp.screen.main.home

import androidx.annotation.StringRes
import com.jcy.dessertorderapp.data.entity.MapSearchInfoEntity

sealed class HomeState{
    object Unititialized: HomeState()

    object Loading: HomeState()

    data class Success(
        val mapSearchInfoEntity: MapSearchInfoEntity,
        val isLocationSame : Boolean
    ): HomeState()

    data class Error(
        @StringRes val messageId: Int
    ) : HomeState()
}
