package com.example.level5task2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.level5task2.data.model.Play
import com.example.level5task2.repository.PlayRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayViewModel(application: Application) : AndroidViewModel(application) {

    private val playRepository: PlayRepository = PlayRepository(application)

    val getAllPlays: LiveData<List<Play>> = playRepository.getGames()

    fun insertPlay(play: Play){
        viewModelScope.launch(Dispatchers.IO) {
            playRepository.insert(play)
        }
    }

    fun deleteAllPlays() {
        viewModelScope.launch(Dispatchers.IO) {
            playRepository.deleteAll()
        }
    }

    fun getTotalWins() = playRepository.getTotalWins()

    fun getTotalLosses() = playRepository.getTotalLosses()

    fun getTotalDraws() = playRepository.getTotalDraws()
}