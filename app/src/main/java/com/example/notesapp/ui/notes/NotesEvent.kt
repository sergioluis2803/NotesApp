package com.example.notesapp.ui.notes

import com.example.notesapp.domain.model.Note

sealed class NotesEvent {
    data class DeleteNote(val note: Note) : NotesEvent()
    data object RestoreNote : NotesEvent()
}