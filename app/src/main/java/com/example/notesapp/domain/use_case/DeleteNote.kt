package com.example.notesapp.domain.use_case

import com.example.notesapp.domain.model.Note
import com.example.notesapp.domain.repository.NoteRepository

class DeleteNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}