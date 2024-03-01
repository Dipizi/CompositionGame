package com.example.compositiongame.domain.entities

data class GameSettings(
    val maxSumValue: Int,
    val minCountRightAnswers: Int,
    val minPercentRightAnswers: Int,
    val timeRoundInSeconds: Int
)
