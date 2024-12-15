package com.example.notetaking.presentation.feature.list

import com.example.core.data.Note

sealed interface ListUiState {
    data object Loading : ListUiState
    data object Empty : ListUiState
    data class Success(val data: List<Note>) : ListUiState
}