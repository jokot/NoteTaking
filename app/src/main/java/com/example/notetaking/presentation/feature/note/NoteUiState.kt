package com.example.notetaking.presentation.feature.note

import com.example.core.data.Note

sealed interface NoteUiState {
    data object Loading : NoteUiState
    data class Edit(val note: Note) : NoteUiState
}