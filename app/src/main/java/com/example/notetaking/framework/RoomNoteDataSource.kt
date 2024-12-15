package com.example.notetaking.framework

import android.content.Context
import com.example.core.data.Note
import com.example.core.repository.NoteDataSource
import com.example.notetaking.framework.db.DatabaseService
import com.example.notetaking.framework.db.NoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomNoteDataSource(
    context: Context
) : NoteDataSource {
    private val noteDao = DatabaseService.getInstance(context).noteDao()

    override suspend fun add(note: Note) = noteDao.addNoteEntity(NoteEntity.fromNote(note))

    override suspend fun get(id: Long): Note? = noteDao.getNoteEntity(id)?.toNote()

    override fun getAll(): Flow<List<Note>> =
        noteDao.getAllNoteEntities().map { it.map(NoteEntity::toNote) }

    override suspend fun remove(note: Note) = noteDao.deleteNoteEntity(NoteEntity.fromNote(note))
}