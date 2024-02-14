package com.example.notesapp.ui.detail

sealed class NoteDetailState {
    data object Loading : NoteDetailState()
    data class Error(val error: String) : NoteDetailState()
    data class Success(val data: String) : NoteDetailState()

}