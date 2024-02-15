package com.example.notesapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.notesapp.databinding.ActivityNoteDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoteDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteDetailBinding
    private val noteDetailViewModel: NoteDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        initUIState()
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                noteDetailViewModel.state.collect {
                    when (it) {
                        NoteDetailState.Loading -> loadingState()
                        is NoteDetailState.Error -> errorState()
                        is NoteDetailState.Success -> successState()
                        else -> {}
                    }
                }
            }
        }
    }

    private fun successState() {

    }

    private fun errorState() {

    }

    private fun loadingState() {

    }
}