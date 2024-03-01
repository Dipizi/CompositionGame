package com.example.compositiongame.domain.entities

data class GameResult(
    val isWin: Boolean,
    val countRightAnswers: Int,
    val totalQuestion: Int,
    val gameSettings: GameSettings
)
