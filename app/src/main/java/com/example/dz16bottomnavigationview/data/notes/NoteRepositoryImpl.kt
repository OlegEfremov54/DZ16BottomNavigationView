package com.example.dz16bottomnavigationview.data.notes

import androidx.lifecycle.LiveData
import com.example.dz16bottomnavigationview.domain.notes.Note
import com.example.dz16bottomnavigationview.domain.notes.NoteRepository

class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {

    override val contacts: LiveData<List<Note>> = noteDao.getAllNotes()

    override suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    override suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

}