package com.anibalofice.loteria.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "bets") // Nome da tabela/entidade
data class Bet(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: String,
    val number: String,
    val date: Date = Date() // inicializar com a data atual
)

