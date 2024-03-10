package com.example.compositiongame.presentation.GameFinishedFragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.compositiongame.R
import com.example.compositiongame.databinding.FragmentGameFinishedBinding
import com.example.compositiongame.domain.entities.GameResult
import com.example.compositiongame.presentation.GameFragment.GameFragment

class GameFinishedFragment : Fragment() {

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw NullPointerException("FragmentGameFinishedBinding is null")

    private lateinit var gameResult: GameResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonTryAgain.setOnClickListener {
            retryAgain()
        }

        val resDrawable = getImageResult(gameResult.isWin)
        binding.imageViewResult.setImageDrawable(resDrawable)

        binding.textViewInfoAboutCountRightAnswers.text = getStringResult(
            R.string.information_about_count_right_answers,
            gameResult.gameSettings.minCountRightAnswers
        )

        binding.textViewScore.text = getStringResult(
            R.string.your_score,
            gameResult.countRightAnswers
        )

        binding.textViewInfoAboutPercentRightAnswers.text = getStringResult(
            R.string.information_about_percent_right_answers,
            gameResult.gameSettings.minPercentRightAnswers
        )

        binding.textViewYourPercent.text = getStringResult(
            R.string.your_score_percent,
            ((gameResult.countRightAnswers / gameResult.totalQuestion.toDouble()) * 100).toInt()
        )

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            retryAgain()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getImageResult(isWin: Boolean): Drawable {
        val resDrawable = if (isWin) {
            AppCompatResources.getDrawable(requireContext(), R.drawable.smile_win)
        } else {
            AppCompatResources.getDrawable(requireContext(), R.drawable.smile_lose)
        }
        return resDrawable ?: throw RuntimeException("Drawable not found")
    }

    private fun getStringResult(resStringId: Int, result: Int): String {
        val resString = resources.getString(resStringId)
        return String.format(resString, result)
    }

    private fun retryAgain() {
        requireActivity().supportFragmentManager.popBackStack(
            GameFragment.NAME_FRAGMENT,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    private fun parseArgs() {
        gameResult = requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT) as GameResult
    }

    companion object {

        private const val KEY_GAME_RESULT = "game_result"
        fun newInstance(result: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT, result)
                }
            }
        }

    }
}