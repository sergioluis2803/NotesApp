package com.example.notesapp.domain.use_case

import com.example.notesapp.domain.model.InvalidNoteException
import com.example.notesapp.domain.model.Note
import com.example.notesapp.domain.repository.NoteRepository
import kotlin.jvm.Throws

class AddNote(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("El título de la nota no puede estar vacío")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("El contenido de la nota no puedo ser vacío")
        }
        repository.insertNote(note)
    }

}