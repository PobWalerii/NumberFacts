package com.example.numberfacts.ui.responsefragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.numberfacts.databinding.FragmentResponseBinding
import com.example.numberfacts.ui.adapter.NumbersFactsAdapter
import com.example.numberfacts.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResponseFragment : Fragment() {
    private var _binding: FragmentResponseBinding? = null
    private val binding get() = _binding!!
    private val args: ResponseFragmentArgs by navArgs()
    private val viewModel by viewModels<ResponseViewModel>()
    private lateinit var adapter: NumbersFactsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentResponseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHomeKey()
        setupAdapter()
        setupRecycler()
        setNumberView()
        loadData()
    }

    private fun setHomeKey() {
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun loadData() {
        lifecycle.coroutineScope.launch {
            viewModel.getNumberFacts(args.number).collect {
                binding.visibleInfoText = it.isEmpty()
                adapter.setList(it, viewModel.currentId)
                viewModel.currentId = -1
            }
        }
    }

    private fun setNumberView() {
        viewModel.currentId = args.currentId
        binding.number = args.number
    }

    private fun setupAdapter() {
        adapter = NumbersFactsAdapter(false)
        adapter.setHasStableIds(true)
    }

    private fun setupRecycler() {
        recyclerView = binding.recycler
        recyclerView.adapter = adapter
        itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recycler: RecyclerView,
                holder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false
            override fun onSwiped(holder: RecyclerView.ViewHolder, dir: Int) {
                deleteFact(holder.adapterPosition)
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun deleteFact(position: Int) {
        val item = adapter.getItemFromPosition(position)
        viewModel.deleteFact(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        itemTouchHelper.attachToRecyclerView(null)
        _binding = null
    }


}