package com.example.compositiongame.presentation.GameFragment

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.compositiongame.R
import com.example.compositiongame.data.GameRepositoryImpl
import com.example.compositiongame.domain.entities.GameResult
import com.example.compositiongame.domain.entities.GameSettings
import com.example.compositiongame.domain.entities.Level
import com.example.compositiongame.domain.entities.Question
import com.example.compositiongame.domain.usecase.GenerateQuestionUseCase
import com.example.compositiongame.domain.usecase.GetGameSettingsUseCase

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GameRepositoryImpl

    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private lateinit var gameSettings: GameSettings
    private lateinit var timer: CountDownTimer

    private var countRightAnswers = 0
    private var countQuestion = 0
    private val context = application

    private val _ldTimeOnRound = MutableLiveData<String>()
    val ldTimeOnRound: LiveData<String>
        get() = _ldTimeOnRound

    private val _ldQuestion = MutableLiveData<Question>()
    val ldQuestion: LiveData<Question>
        get() = _ldQuestion

    private val _ldPercentRightAnswers = MutableLiveData<Int>()
    val ldPercentRightAnswers: LiveData<Int>
        get() = _ldPercentRightAnswers

    private val _ldProgressAnswers = MutableLiveData<String>()
    val ldProgressAnswers: LiveData<String>
        get() = _ldProgressAnswers

    private val _ldEnoughCountRightAnswers = MutableLiveData<Boolean>()
    val ldEnoughCountRightAnswers: LiveData<Boolean>
        get() = _ldEnoughCountRightAnswers

    private val _ldEnoughPercentRightAnswers = MutableLiveData<Boolean>()
    val ldEnoughPercentRightAnswers: LiveData<Boolean>
        get() = _ldEnoughPercentRightAnswers

    private val _ldMinPercentRightAnswers = MutableLiveData<Int>()
    val ldMinPercentRightAnswers: LiveData<Int>
        get() = _ldMinPercentRightAnswers

    private val _ldGameResult = MutableLiveData<GameResult>()
    val ldGameResult: LiveData<GameResult>
        get() = _ldGameResult

    fun startGame(level: Level) {
        initVariables(level)
        startTimer()
        generateQuestion()
        updateProgress()
    }

    private fun initVariables(level: Level) {
        gameSettings = getGameSettingsUseCase.invoke(level)
        _ldMinPercentRightAnswers.value = gameSettings.minPercentRightAnswers
    }

   private fun startTimer() {
        val timeOnRound = gameSettings.timeRoundInSeconds * SECONDS_IN_MILLIS
        timer = object : CountDownTimer(timeOnRound, SECONDS_IN_MILLIS) {
            override fun onTick(millisUntilFinished: Long) {
                _ldTimeOnRound.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer.start()
    }

    private fun formatTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / SECONDS_IN_MILLIS
        val minutes = seconds / MINUTES_IN_SECONDS
        val leftSeconds = seconds - (minutes * MINUTES_IN_SECONDS)
        return String.format(FORMAT_TIME, minutes, leftSeconds)
    }

    private fun generateQuestion() {
        _ldQuestion.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generateQuestion()
    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = ldQuestion.value?.rightAnswer
        if (number == rightAnswer) {
            countRightAnswers++
        }
        countQuestion++
    }

    private fun updateProgress() {
        val percent = calculatePercentRightAnswers()
        _ldPercentRightAnswers.value = percent
        _ldProgressAnswers.value = String.format(
            context.resources.getString(R.string.information_about_answers),
            countRightAnswers,
            gameSettings.minCountRightAnswers
        )
        _ldEnoughCountRightAnswers.value = countRightAnswers >= gameSettings.minCountRightAnswers
        _ldEnoughPercentRightAnswers.value = percent >= gameSettings.minPercentRightAnswers
    }

    private fun calculatePercentRightAnswers(): Int {
        return ((countRightAnswers / countQuestion.toDouble()) * 100).toInt()
    }

    private fun finishGame() {
        _ldGameResult.value = GameResult(
            _ldEnoughCountRightAnswers.value == true && _ldEnoughPercentRightAnswers.value == true,
            countRightAnswers,
            countQuestion,
            gameSettings
        )
    }
    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }

    companion object {
        private const val FORMAT_TIME = "%02d:%02d"
        private const val SECONDS_IN_MILLIS = 1000L
        private const val MINUTES_IN_SECONDS = 60
    }
}