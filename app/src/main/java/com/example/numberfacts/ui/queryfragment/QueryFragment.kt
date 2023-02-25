package com.example.numberfacts.ui.queryfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.numberfacts.R
import com.example.numberfacts.databinding.FragmentQueryBinding
import com.example.numberfacts.utils.FactsLoaded
import com.example.numberfacts.utils.FactsLoading
import com.example.numberfacts.utils.LoadError
import com.example.numberfacts.utils.NumbersUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QueryFragment : Fragment() {
    private var _binding: FragmentQueryBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<QueryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentQueryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtonClickListener()
        observeUIState()
    }

    private fun setupButtonClickListener() {
        binding.buttonQuery.setOnClickListener {
            val queryKey = binding.queryNumber.text.toString().toInt()
            if(queryKey!=0) {
                viewModel.getNumbersFacts(queryKey)
            }
        }
        binding.buttonRandom.setOnClickListener {
            viewModel.getNumbersFacts(0)
        }
    }

    private fun observeUIState() {
        viewModel.state.observe(viewLifecycleOwner, ::handleUiState)
    }

    private fun handleUiState(state: NumbersUiState) {
        when (state) {
            is LoadError -> {
                Toast.makeText(
                    requireContext(), state.message, Toast.LENGTH_SHORT
                ).show()
            }
            is FactsLoaded -> {
                state.data.apply {
                    Toast.makeText(context, if(this.isEmpty()) getString(R.string.response_is_empty) else this, Toast.LENGTH_LONG).show()
                }
            }
            is FactsLoading -> {
                binding.visibleProgress = state.isLoading
            }
        }
    }



}