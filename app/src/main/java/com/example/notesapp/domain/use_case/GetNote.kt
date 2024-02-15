package com.example.notesapp.domain.use_case

import com.example.notesapp.domain.model.Note
import com.example.notesapp.domain.repository.NoteRepository

class GetNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}