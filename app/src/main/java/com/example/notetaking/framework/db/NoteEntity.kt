package com.example.notetaking.framework.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.data.Note

@Entity(tableName = "note")
data class NoteEntity(
    val title: String,
    val content: String,

    @ColumnInfo(name = "creation_date")
    val creationTime: Long,
    @ColumnInfo(name = "update_time")
    val updateTime: Long,

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L
) {
    companion object {
        fun fromNote(note: Note): NoteEntity = NoteEntity(
            title = note.title,
            content = note.content,
            creationTime = note.creationTime,
            updateTime = note.updateTime,
            id = note.id
        )
    }

    fun toNote(): Note = Note(
        title = title,
        content = content,
        creationTime = creationTime,
        updateTime = updateTime,
        id = id
    )
}
