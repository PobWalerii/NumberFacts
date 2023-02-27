package com.example.numberfacts.ui.queryfragment

sealed class NumbersUiState
    data class FactsLoaded(val data: String) : NumbersUiState()
    data class FactsLoading(val isLoading: Boolean) : NumbersUiState()
    data class LoadError(val message: String?) : NumbersUiState()


