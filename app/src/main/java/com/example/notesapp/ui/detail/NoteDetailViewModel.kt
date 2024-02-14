package com.example.notesapp.ui.detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor() : ViewModel() {

    private var _state = MutableStateFlow<NoteDetailState>(NoteDetailState.Loading)
    val state: StateFlow<NoteDetailState> = _state

}