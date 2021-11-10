package com.jcy.dessertorderapp.screen.main.home

sealed class HomeState{
    object Unititialized: HomeState()

    object Loading: HomeState()

    object Success: HomeState()
}
