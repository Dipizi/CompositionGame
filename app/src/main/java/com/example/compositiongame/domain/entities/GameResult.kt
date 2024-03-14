package com.example.compositiongame.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult(
    val isWin: Boolean,
    val countRightAnswers: Int,
    val totalQuestion: Int,
    val gameSettings: GameSettings
): Parcelable