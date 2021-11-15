package com.jcy.dessertorderapp.data.entity

import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@androidx.room.Entity
@Parcelize
data class LocationLatLngEntity (
    val latitude : Double,
    val longitude: Double,
    @PrimaryKey(autoGenerate = true)
    override val id: Long= -1,
): Entity, Parcelable