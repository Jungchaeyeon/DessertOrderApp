package com.jcy.dessertorderapp.util.convertor

import androidx.room.TypeConverter

object RoomTypeConvertors {

    @TypeConverter
    @JvmStatic
    fun toString(pair: Pair<Int,Int>): String{
        return "${pair.first},${pair.second}"
    }
    @TypeConverter
    @JvmStatic
    fun toIntPair(str: String): Pair<Int,Int> {
        val splitedStr = str.split(",")
        return Pair(Integer.parseInt(splitedStr[0].trim()), Integer.parseInt(splitedStr[1].trim()))
    }
}