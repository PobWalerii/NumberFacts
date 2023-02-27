package com.example.numberfacts.ui.queryfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.numberfacts.data.database.entitys.Numbers
import com.example.numberfacts.data.repository.NumbersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

@HiltViewModel
class QueryViewModel @Inject constructor(
    private val repository: NumbersRepository
): ViewModel() {

    private var _state: MutableLiveData<NumbersUiState> = MutableLiveData()
    val state: LiveData<NumbersUiState> = _state

    var currentId = 0L

    fun getNumbersFacts(key: Int?) {
        viewModelScope.launch {
            repository
                .getNumbersFacts(key)
                .collect(::handleResponse)
        }
    }

    private fun handleResponse(resourse: ResponseState<String>) {
        when (resourse) {
            is ResponseState.Error -> {
                _state.value = LoadError(resourse.message)
            }
            is ResponseState.Loading -> {
                _state.value = FactsLoading(resourse.isLoading)
            }
            is ResponseState.Success -> {
                _state.value = FactsLoaded(resourse.data ?: "")
            }
        }
    }

    fun getNumbersList(): Flow<List<Numbers>> = repository.getNumbersList()

}