package com.example.compositiongame.domain.usecase

import com.example.compositiongame.domain.entities.Question
import com.example.compositiongame.domain.repository.GameRepository

class GenerateQuestionUseCase(private val repository: GameRepository) {

    operator fun invoke(maxSumValue: Int): Question =
        repository.generationQuestion(maxSumValue, COUNT_OPTIONS_ANSWERS)

    companion object {

        const val COUNT_OPTIONS_ANSWERS = 6
    }
}