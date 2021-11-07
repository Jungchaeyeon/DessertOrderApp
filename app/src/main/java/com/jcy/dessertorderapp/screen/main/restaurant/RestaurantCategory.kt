package com.jcy.dessertorderapp.screen.main.restaurant

import androidx.annotation.StringRes
import com.jcy.dessertorderapp.R

enum class RestaurantCategory(
    @StringRes val categoryNameId: Int,
    @StringRes val categoryTypeId: Int,
){
    ALL(R.string.all,R.string.all_type)
}