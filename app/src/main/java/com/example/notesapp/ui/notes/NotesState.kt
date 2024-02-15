package com.example.notesapp.ui.notes

import com.example.notesapp.domain.model.Note

data class NotesState(
    val notes: List<Note> = emptyList()
)