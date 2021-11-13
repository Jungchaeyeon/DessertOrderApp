package com.jcy.dessertorderapp.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MapSearchInfoEntity (
    val fullAddress: String,
    val name: String,
    val locationLatLngEntity: LocationLatLngEntity
): Parcelable