package com.anibalofice.loteria.data

import androidx.room.Dao
import androidx.room.Insert

@Dao //Data Acces Object
interface BetDao {
    @Insert
    fun insert(bet: Bet)
}