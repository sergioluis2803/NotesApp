package com.example.notesapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.domain.model.Note
import com.example.notesapp.domain.use_case.NoteUseCases
import com.example.notesapp.ui.notes.NotesEvent
import com.example.notesapp.ui.notes.NotesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCases: NoteUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(NotesState(notes = emptyList()))
    val state: StateFlow<NotesState> = _state

    private var getNotesJob: Job? = null
    private var recentlyDeletedNote: Note? = null

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    useCases.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }

            NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    useCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
        }
    }


    fun getNotesSearch(query: String) {
        if (query.isNotEmpty()) {
            getNotesJob?.cancel()
            getNotesJob = useCases.getNotesSearch(query)
                .onEach { notes ->
                    _state.value = state.value.copy(
                        notes = notes
                    )
                }
                .launchIn(viewModelScope)
        } else {
            _state.value = state.value.copy(emptyList())
        }
    }

}