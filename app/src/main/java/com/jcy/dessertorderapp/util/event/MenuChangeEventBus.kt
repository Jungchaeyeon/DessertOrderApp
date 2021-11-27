package com.jcy.dessertorderapp.util.event

import com.jcy.dessertorderapp.screen.MainTabMenu
import kotlinx.coroutines.flow.MutableSharedFlow

class MenuChangeEventBus {

    val mainTabMenuFlow = MutableSharedFlow<MainTabMenu>()

    suspend fun changeMenu(menu: MainTabMenu){
        mainTabMenuFlow.emit(menu)
    }
}