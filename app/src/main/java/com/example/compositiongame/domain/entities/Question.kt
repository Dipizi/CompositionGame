package com.example.compositiongame.domain.entities

data class Question(
    val sum: Int,
    val visibleNumber: Int,
    val optionsAnswer: List<Int>
) {
    val rightAnswer: Int
        get() = sum - visibleNumber
}
