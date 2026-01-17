package com.anibalofice.loteria.data

import androidx.room.TypeConverter
import java.util.Date

class Converters {
    // metodo para converter o tipo Date para Timestamp

    //1. Data -> Numero
    @TypeConverter
    fun dateToTimestamp(data: Date): Long{
        return data.time
    }

    // 2. Numero(long) -> Data
    @TypeConverter
    fun timestampToDate(time: Long): Date{
        return Date(time)
    }

}