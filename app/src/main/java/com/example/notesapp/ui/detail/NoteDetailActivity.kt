package com.example.notesapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import com.example.notesapp.R
import com.example.notesapp.databinding.ActivityNoteDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoteDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteDetailBinding
    private val noteDetailViewModel: NoteDetailViewModel by viewModels()

    private val args: NoteDetailActivityArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        args.id
    }

    private fun initUI() {
        initUIState()
        initListener()
        initViewsColors()
    }

    private fun initViewsColors() {
        with(binding) {
            view1.setBackgroundColor(
                ContextCompat.getColor(
                    this@NoteDetailActivity,
                    R.color.redOrange
                )
            )
            view2.setBackgroundColor(
                ContextCompat.getColor(
                    this@NoteDetailActivity,
                    R.color.redPink
                )
            )
            view3.setBackgroundColor(
                ContextCompat.getColor(
                    this@NoteDetailActivity,
                    R.color.babyBlue
                )
            )
            view4.setBackgroundColor(
                ContextCompat.getColor(
                    this@NoteDetailActivity,
                    R.color.violet
                )
            )
            view5.setBackgroundColor(
                ContextCompat.getColor(
                    this@NoteDetailActivity,
                    R.color.lightGreen
                )
            )
        }
    }

    private fun initListener() {
        binding.btnSaveNote.setOnClickListener {
            noteDetailViewModel.onEvent(AddEditNoteEvent.SaveNote)
        }

        with(binding) {
            view1.setOnClickListener { noteDetailViewModel.onEvent(AddEditNoteEvent.ChangeColor(R.color.redOrange)) }
            view2.setOnClickListener { noteDetailViewModel.onEvent(AddEditNoteEvent.ChangeColor(R.color.redPink)) }
            view3.setOnClickListener { noteDetailViewModel.onEvent(AddEditNoteEvent.ChangeColor(R.color.babyBlue)) }
            view4.setOnClickListener { noteDetailViewModel.onEvent(AddEditNoteEvent.ChangeColor(R.color.violet)) }
            view5.setOnClickListener { noteDetailViewModel.onEvent(AddEditNoteEvent.ChangeColor(R.color.lightGreen)) }
        }
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                noteDetailViewModel.eventFlow.collectLatest { event ->
                    when (event) {
                        NoteDetailViewModel.UiEvent.SaveNote -> {
                            successState()
                        }

                        is NoteDetailViewModel.UiEvent.ShowSnackbar -> {
                            errorState()
                        }
                    }
                }
            }
        }
    }

    private fun successState() {
        Toast.makeText(
            this@NoteDetailActivity,
            "NOTA GUARDADA",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun errorState() {
        Toast.makeText(
            this@NoteDetailActivity,
            "HUBO UN ERROR",
            Toast.LENGTH_SHORT
        ).show()
    }

}