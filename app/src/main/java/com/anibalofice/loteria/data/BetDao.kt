package com.anibalofice.loteria.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao //Data Acces Object
interface BetDao {
    @Insert
    fun insert(bet: Bet)
    @Query("SELECT * FROM bets WHERE type = :betType")
    fun getNumbersByType(betType: String): List<Bet>





}