package com.example.core.usecase

import com.example.core.repository.NoteRepository

class GetAllNotes(
    private val noteRepository: NoteRepository
) {
    operator fun invoke() = noteRepository.getAllNotes()
}