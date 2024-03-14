package com.example.compositiongame.utils

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.compositiongame.R
import com.example.compositiongame.domain.entities.GameResult

@BindingAdapter("InfoAboutCountRightAnswers")
fun bindInfoAboutCountRightAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.information_about_count_right_answers),
        count
    )
}

@BindingAdapter("YourScore")
fun bindScore(textView: TextView, score: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.your_score),
        score
    )
}

@BindingAdapter("YourPercent")
fun bindYourPercent(textView: TextView, gameResult: GameResult) {
    val percent =
        ((gameResult.countRightAnswers / gameResult.totalQuestion.toDouble()) * 100).toInt()
    textView.text = String.format(
        textView.context.getString(R.string.your_score_percent),
        percent
    )
}

@BindingAdapter("InfoAboutPercentRightAnswers")
fun bindInfoAboutPercentRightAnswers(textView: TextView, percent: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.information_about_percent_right_answers),
        percent
    )
}

@BindingAdapter("setupImageResult")
fun bindSetupImageResult(imageView: ImageView, isWin: Boolean) {
    val resDrawable = if (isWin) {
        R.drawable.smile_win
    } else {
        R.drawable.smile_lose
    }
    imageView.setImageResource(resDrawable)
}

@BindingAdapter("sumValue")
fun bindSumValue(textView: TextView, sumValue: Int) {
    textView.text = sumValue.toString()
}

@BindingAdapter("visibleNumber")
fun bindVisibleNumber(textView: TextView, visibleNumber: Int) {
    textView.text = visibleNumber.toString()
}

@BindingAdapter("percentRightAnswers")
fun bindPercentRightAnswers(progressBar: ProgressBar, minPercent: Int) {
    progressBar.setProgress(minPercent, true)
}

@BindingAdapter("setupColorByRightCountAnswers")
fun bindColorByRightCountAnswers(textView: TextView, isGoodState: Boolean ) {
    val color = getColorByState(textView,isGoodState)
    textView.setTextColor(color)
}

@BindingAdapter("setupColorByPercentRightAnswers")
fun bindColorByPercentRightAnswers(progressBar: ProgressBar , isGoodState: Boolean ) {
    val color = getColorByState(progressBar,isGoodState)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

@BindingAdapter("setOnClickListener")
fun bindOnClickListener(textView: TextView, onClick: OnClickOptionListener) {
    textView.setOnClickListener {
        onClick.onClickOption(textView.text.toString().toInt())
    }
}

interface OnClickOptionListener {
    fun onClickOption(number: Int)
}

private fun getColorByState(view: View, isGoodState: Boolean): Int {
    val resColorId = if (isGoodState) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return ContextCompat.getColor(view.context, resColorId)
}


