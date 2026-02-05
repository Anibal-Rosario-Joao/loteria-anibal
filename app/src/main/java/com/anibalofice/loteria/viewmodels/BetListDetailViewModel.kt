package com.anibalofice.loteria.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anibalofice.loteria.data.Bet
import com.anibalofice.loteria.data.BetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BetListDetailViewModel(
    private val type: String,
    private val betRepository: BetRepository
) : ViewModel(){
    private val _bets = MutableStateFlow<List<Bet>>(emptyList())
    val bets: StateFlow<List<Bet>> = _bets.asStateFlow()



    init {
        viewModelScope.launch {
            val result = betRepository.getNumbersByType(type)
            _bets.value = result
        }
    }

}