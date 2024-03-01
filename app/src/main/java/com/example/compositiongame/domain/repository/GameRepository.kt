package com.example.compositiongame.domain.repository

import com.example.compositiongame.domain.entities.GameSettings
import com.example.compositiongame.domain.entities.Level
import com.example.compositiongame.domain.entities.Question

interface GameRepository {

    fun generationQuestion(
        maxSumValue: Int,
        countOptionAnswers: Int
    ): Question

    fun getGameSettings(level: Level): GameSettings
}