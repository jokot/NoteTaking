package com.example.notetaking.framework

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notetaking.presentation.feature.list.ListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _uiState: MutableStateFlow<ListUiState> = MutableStateFlow(ListUiState.Loading)
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    private val ioDispatcher = Dispatchers.IO

    init {
        observeGetNotes()
    }

    fun observeGetNotes() {
        viewModelScope.launch(ioDispatcher) {
            _uiState.update { ListUiState.Loading }
            useCases.getAllNotes().collect { notes ->
                _uiState.update {
                    if (notes.isEmpty()) {
                        ListUiState.Empty
                    } else {
                        ListUiState.Success(
                            notes.map { note ->
                                note.copy(wordCount = useCases.getWordCount(note))
                            }
                        )
                    }
                }
            }
        }
    }
}