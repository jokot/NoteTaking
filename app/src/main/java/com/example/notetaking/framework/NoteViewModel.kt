package com.example.notetaking.framework

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.Note
import com.example.notetaking.presentation.feature.note.NoteUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _uiState: MutableStateFlow<NoteUiState> = MutableStateFlow(NoteUiState.Loading)
    val uiState: StateFlow<NoteUiState> = _uiState.asStateFlow()

    private val _note: MutableStateFlow<Note> = MutableStateFlow(Note())

    private val ioDispatcher = Dispatchers.IO

    val saved = MutableStateFlow(false)

    fun saveNote(note: Note) {
        viewModelScope.launch(ioDispatcher) {
            _uiState.update { NoteUiState.Edit(note) }
            _note.update { note }
            useCases.addNote(note)
            saved.update { true }
        }
    }

    fun getNote(id: Long) {
        viewModelScope.launch(ioDispatcher) {
            _uiState.update { NoteUiState.Loading }
            val note = useCases.getNote(id) ?: Note()
            _uiState.update { NoteUiState.Edit(note) }
            _note.update { note }
        }
    }

    fun removeNote() {
        viewModelScope.launch(ioDispatcher) {
            val note = _note.value
            Log.d("removeNote", "$note")
            useCases.removeNote(note)
        }
    }

    fun onAddNewNote() {
        _uiState.update { NoteUiState.Loading }
        _uiState.update { NoteUiState.Edit(Note()) }
        _note.update { (Note()) }
    }

    fun onTitleChange(title: String) {
        val updatedNote = _note.value.copy(title = title)
        _uiState.update { NoteUiState.Edit(updatedNote) }
        _note.update { updatedNote }
    }

    fun onContentChange(content: String) {
        val updatedNote = _note.value.copy(content = content)
        _uiState.update { NoteUiState.Edit(updatedNote) }
        _note.update { updatedNote }
    }

    fun onTriggerSave() {
        val time = System.currentTimeMillis()
        val creationTime = if (_note.value.creationTime == 0L) time else
            _note.value.creationTime

        saveNote(
            _note.value.copy(
                updateTime = time,
                creationTime = creationTime
            ).also { note ->
                _note.update { note }
                _uiState.update { NoteUiState.Edit(note) }
            }
        )
    }
}