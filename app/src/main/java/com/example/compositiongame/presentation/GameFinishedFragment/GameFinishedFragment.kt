package com.example.compositiongame.presentation.GameFinishedFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
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

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            retryAgain()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun retryAgain() {
        requireActivity().supportFragmentManager.popBackStack(GameFragment.NAME_FRAGMENT, FragmentManager.POP_BACK_STACK_INCLUSIVE)
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