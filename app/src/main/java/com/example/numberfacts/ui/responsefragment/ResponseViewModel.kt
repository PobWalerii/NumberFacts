package com.example.numberfacts.ui.responsefragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.numberfacts.data.database.entitys.Numbers
import com.example.numberfacts.data.repository.NumbersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResponseViewModel @Inject constructor(
    private val repository: NumbersRepository
): ViewModel() {

    var currentId: Long = 0

    fun getNumberFacts(search: Int): Flow<List<Numbers>> = repository.getNumberFacts(search)

    fun deleteFact(item: Numbers) {
        viewModelScope.launch {
            repository.deleteFact(item)
        }
    }

}