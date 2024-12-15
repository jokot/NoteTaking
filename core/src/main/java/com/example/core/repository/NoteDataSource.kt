package com.example.core.repository

import com.example.core.data.Note
import kotlinx.coroutines.flow.Flow

interface NoteDataSource {
    suspend fun add(note: Note)
    suspend fun get(id: Long): Note?
    fun getAll(): Flow<List<Note>>
    suspend fun remove(note: Note)
}