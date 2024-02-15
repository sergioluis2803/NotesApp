package com.example.notesapp.ui.notes

import androidx.lifecycle.ViewModel
import com.example.notesapp.domain.model.Note
import com.example.notesapp.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private var _state = MutableStateFlow<List<Note>>(emptyList())
    val state: StateFlow<List<Note>> = _state

    private var getNotesJob: Job? = null
    init {
        getNotes()
        //_notes.value = listOf( Note("HOLA", "Descrition", ))
    }

    private fun getNotes(){
        getNotesJob?.cancel()

    }

}