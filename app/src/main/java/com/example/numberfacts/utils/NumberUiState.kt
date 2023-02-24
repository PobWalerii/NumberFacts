package com.example.numberfacts.utils

import com.example.numberfacts.data.database.entitys.Numbers

sealed class NumbersUiState
    data class FactsLoaded(val data: Numbers) : NumbersUiState()
    data class ListLoading(val isLoading: Boolean) : NumbersUiState()
    data class ListError(val message: String?) : NumbersUiState()


