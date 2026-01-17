package com.anibalofice.loteria.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlin.concurrent.Volatile

@TypeConverters(Converters::class) // Classe que converte o Date
@Database(entities = [Bet::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    // codigo padrao
    abstract fun betDao(): BetDao

    // como nao dá para instanciar directamente uma abtrast class
    // usaremos uma companion object
    companion object{
        //variavel global
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase{
            // retornar SEMPRE o mesmo objecto
            return instance ?: synchronized(this){
                buildDatabase(context).also {
                    instance =it
                }
            }

        }

        // codigo padrao
        //função para criar o banco de dado
        private fun buildDatabase(context: Context): AppDatabase{
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "loteria_app"
            ).build()

        }
    }
}