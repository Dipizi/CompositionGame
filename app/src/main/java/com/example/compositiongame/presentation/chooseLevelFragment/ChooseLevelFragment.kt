package com.example.compositiongame.presentation.chooseLevelFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.compositiongame.databinding.FragmentChooseLevelBinding
import com.example.compositiongame.domain.entities.Level


class ChooseLevelFragment : Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw NullPointerException("FragmentChooseLevelBinding is null")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonTestLevel.setOnClickListener {
            launchGameFragment(Level.TEST)
        }

        binding.buttonEasyLevel.setOnClickListener {
            launchGameFragment(Level.EASY)
        }

        binding.buttonMediumLevel.setOnClickListener {
            launchGameFragment(Level.NORMAL)
        }

        binding.buttonHardLevel.setOnClickListener {
            launchGameFragment(Level.HARD)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchGameFragment(level: Level) {
        findNavController().navigate(
            ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(level)
        )
    }
}