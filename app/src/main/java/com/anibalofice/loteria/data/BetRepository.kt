package com.anibalofice.loteria.data

class BetRepository(private val betDao: BetDao) {
    fun getNumbersByType(betType: String): List<Bet> {
        return betDao.getNumbersByType(betType)
    }
    fun insert(bet: Bet) {
        betDao.insert(bet)
    }

    companion object{
        private var instance: BetRepository? = null
        fun getInstance(betDao: BetDao): BetRepository {
            return instance ?: synchronized(this) {
                instance ?: BetRepository(betDao).also { instance = it }
            }
        }

    }
}