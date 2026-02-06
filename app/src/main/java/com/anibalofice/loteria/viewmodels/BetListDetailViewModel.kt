package com.anibalofice.loteria.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.anibalofice.loteria.App
import com.anibalofice.loteria.data.Bet
import com.anibalofice.loteria.data.BetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BetListDetailViewModel(
    private val saveStateHandle: SavedStateHandle,
    private val betRepository: BetRepository
) : ViewModel(){
    private val _bets = MutableStateFlow<List<Bet>>(emptyList())
    val bets: StateFlow<List<Bet>> = _bets.asStateFlow()



    init {
        viewModelScope.launch {
            val type = saveStateHandle.get<String>("type")?: throw Exception("Tipo não encontrado")
            val result = betRepository.getNumbersByType(type)
            _bets.value = result
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = extras[APPLICATION_KEY]
                val dao = (application as App).db.betDao()
                val repository = BetRepository.getInstance(dao)

                val saveStateHandle = extras.createSavedStateHandle()
                return BetListDetailViewModel(saveStateHandle, repository) as T

            }
            }
    }

}