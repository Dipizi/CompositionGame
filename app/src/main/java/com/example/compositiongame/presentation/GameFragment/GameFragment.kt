package com.example.compositiongame.presentation.GameFragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.compositiongame.R
import com.example.compositiongame.databinding.FragmentGameBinding
import com.example.compositiongame.domain.entities.GameResult
import com.example.compositiongame.domain.entities.Level
import com.example.compositiongame.presentation.GameFinishedFragment.GameFinishedFragment

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw NullPointerException("FragmentGameBinding is null")

    private lateinit var level: Level

    private val listAnswers by lazy {
        mutableListOf<TextView>().apply {
            add(binding.textViewFirstAnswer)
            add(binding.textViewSecondAnswer)
            add(binding.textViewThirdAnswer)
            add(binding.textViewFourthAnswer)
            add(binding.textViewFifthAnswer)
            add(binding.textViewSixthAnswer)
        }
    }

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[GameViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        liveDataObserve()
        setupOnClickListenerOnTextViewAnswers()
        viewModel.startGame(level)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun liveDataObserve() {

        viewModel.ldTimeOnRound.observe(viewLifecycleOwner) {
            binding.textViewTimeRound.text = it
        }

        viewModel.ldQuestion.observe(viewLifecycleOwner) { question ->
            binding.textViewSumValue.text = question.sum.toString()
            binding.textViewVisibleNumber.text = question.visibleNumber.toString()

            val shuffledList = question.optionsAnswer.shuffled()
            for (index in listAnswers.indices) {
                listAnswers[index].text = shuffledList[index].toString()
            }
        }

        viewModel.ldPercentRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBarRightAnswers.setProgress(it, true)
        }

        viewModel.ldEnoughCountRightAnswers.observe(viewLifecycleOwner) { isGoodState ->
            val color = getColorByState(isGoodState)
            binding.textViewInfoAboutAnswers.setTextColor(color)
        }

        viewModel.ldEnoughPercentRightAnswers.observe(viewLifecycleOwner) { isGoodState ->
            val color = getColorByState(isGoodState)
            binding.progressBarRightAnswers.progressTintList = ColorStateList.valueOf(color)
        }

        viewModel.ldMinPercentRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBarRightAnswers.secondaryProgress = it
        }

        viewModel.ldProgressAnswers.observe(viewLifecycleOwner) {
            binding.textViewInfoAboutAnswers.text = it
        }

        viewModel.ldGameResult.observe(viewLifecycleOwner) { gameResult ->
            launchGameFinishedFragment(gameResult)
        }
    }

    private fun getColorByState(isGoodState: Boolean): Int {
        val resColorId = if (isGoodState) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireContext(), resColorId)
    }

    private fun setupOnClickListenerOnTextViewAnswers() {
        for (textView in listAnswers) {
            textView.setOnClickListener {
                viewModel.chooseAnswer(textView.text.toString().toInt())
            }
        }
    }

    private fun parseArgs() {
       level = requireArguments().getParcelable<Level>(KEY_LEVEL) as Level
    }

    private fun launchGameFinishedFragment(result: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container_view, GameFinishedFragment.newInstance(result))
            .commit()
    }

    companion object {

        const val NAME_FRAGMENT = "GameFragment"

        private const val KEY_LEVEL = "level"
        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }
}