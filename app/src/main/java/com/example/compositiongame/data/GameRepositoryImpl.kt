package com.example.compositiongame.data

import com.example.compositiongame.domain.entities.GameSettings
import com.example.compositiongame.domain.entities.Level
import com.example.compositiongame.domain.entities.Question
import com.example.compositiongame.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl: GameRepository {

    private const val MIN_SUM_VALUE = 2
    private const val  MIN_VISIBLE_NUMBER = 1
    override fun generationQuestion(maxSumValue: Int, countOptionAnswers: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_VISIBLE_NUMBER, sum)
        val rightAnswer = sum - visibleNumber
        val setOptionsAnswer = hashSetOf<Int>()
        setOptionsAnswer.add(rightAnswer)
        val from = max(rightAnswer - countOptionAnswers, MIN_VISIBLE_NUMBER)
        val to = min(rightAnswer + countOptionAnswers, maxSumValue)
        while (setOptionsAnswer.size < countOptionAnswers) {
            setOptionsAnswer.add(Random.nextInt(from, to))
        }
        return Question(sum, visibleNumber, setOptionsAnswer.shuffled())
    }

    override fun getGameSettings(level: Level): GameSettings {

        return when (level) {
            Level.TEST -> GameSettings(
                10,
                3,
                10,
                8
            )

            Level.EASY -> GameSettings(
                10,
                10,
                70,
                60
            )

            Level.NORMAL -> GameSettings(
                20,
                20,
                80,
                40
            )

            Level.HARD -> GameSettings(
                30,
                30,
                90,
                40
            )
        }
    }
}