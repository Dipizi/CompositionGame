package com.example.compositiongame.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compositiongame.domain.entities.Level
import com.example.compositiongame.presentation.gameFragment.GameViewModel

class GameViewModelFactory(
    private val level: Level,
    private val application: Application
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)){
            return GameViewModel(level, application) as T
        }
        throw RuntimeException("Unknown class view model: ${modelClass.name}")
    }

}