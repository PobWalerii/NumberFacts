package com.example.numberfacts.ui.queryfragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.numberfacts.R
import com.example.numberfacts.data.database.entitys.Numbers
import com.example.numberfacts.databinding.FragmentQueryBinding
import com.example.numberfacts.ui.adapter.NumbersFactsAdapter
import com.example.numberfacts.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QueryFragment : Fragment() {
    private var _binding: FragmentQueryBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<QueryViewModel>()
    private lateinit var adapter: NumbersFactsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupAdapter()
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
        setHomeKey()
        setupRecycler()
        loadData()
        setupButtonClickListener()
        observeUIState()
        setupItemClickListener()
    }

    private fun loadData() {
        lifecycle.coroutineScope.launch {
            viewModel.getNumbersList().collect {
                binding.visibleInfoText = it.isEmpty()
                adapter.setList(it,viewModel.currentId)
                if(viewModel.currentId == 0L) {
                    recyclerView.layoutManager?.scrollToPosition(0)
                } else {
                    viewModel.currentId = 0L
                }
            }
        }
    }

    private fun setupAdapter() {
        adapter = NumbersFactsAdapter(true)
        adapter.setHasStableIds(true)
    }

    private fun setupRecycler() {
        recyclerView = binding.recycler
        recyclerView.adapter = adapter
    }

    private fun setupItemClickListener() {
        adapter.setOnItemClickListener(object : NumbersFactsAdapter.OnItemClickListener {
            override fun onItemClick(current: Numbers) {
                viewModel.currentId = -1L
                findNavController().navigate(
                    QueryFragmentDirections.actionQueryFragmentToResponseFragment(current.number,current.id)
                )
            }
        })
    }
    private fun setupButtonClickListener() {
        binding.buttonQuery.setOnClickListener {
            val queryKey = binding.queryNumber.text.toString()
            if(queryKey.isEmpty()) {
                Toast.makeText(context, R.string.empty_query, Toast.LENGTH_LONG).show()
            } else {
                hideKeyboardFromView(requireContext(),binding.queryNumber)
                val key = queryKey.toInt()
                //Toast.makeText(context,"$key",Toast.LENGTH_LONG).show()
                viewModel.getNumbersFacts(queryKey.toInt())
            }
        }
        binding.buttonRandom.setOnClickListener {
            hideKeyboardFromView(requireContext(),binding.queryNumber)
            viewModel.getNumbersFacts(null)
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
                    Toast.makeText(context,
                        this.ifEmpty { getString(R.string.response_is_empty) }, Toast.LENGTH_LONG).show()
                }
            }
            is FactsLoading -> {
                binding.visibleProgress = state.isLoading
            }
        }
    }

    private fun setHomeKey() {
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun hideKeyboardFromView(context: Context, view: View) {
        val manager: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(view.windowToken, 0)
    }



}