package com.example.dz16bottomnavigationview.domain.notes

import androidx.lifecycle.LiveData

interface NoteRepository {

    val contacts: LiveData<List<Note>>

    suspend fun insert(note: Note)
    suspend fun delete(note: Note)
}