package com.jcy.dessertorderapp.screen.main.restaurant.detail

import androidx.annotation.StringRes
import com.jcy.dessertorderapp.R

enum class RestaurantCategoryDetail(
    @StringRes val categoryNameId: Int
) {
    MENU(R.string.menu), REVIEW(R.string.review)
}