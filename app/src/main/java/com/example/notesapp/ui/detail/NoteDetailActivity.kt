package com.example.notesapp.ui.detail

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
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

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                noteDetailViewModel.noteTitle.collect {
                    updateTitleNote(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                noteDetailViewModel.noteColor.collect {
                    updateColorNote(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                noteDetailViewModel.noteContent.collect {
                    updateContentNote(it)
                }
            }
        }

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

        binding.etTitle.addTextChangedListener(validationTitle)
        binding.etDescription.addTextChangedListener(validationContent)

        val noteId = args.id
        noteDetailViewModel.getNoteDetail(noteId)
    }

    private val validationTitle = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable?) {
            noteDetailViewModel.onEvent(AddEditNoteEvent.EnteredTitle(p0.toString()))
        }
    }

    private val validationContent = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(p0: Editable?) {
            noteDetailViewModel.onEvent(AddEditNoteEvent.EnteredContent(p0.toString()))
        }

    }

    private fun updateContentNote(it: NoteTextFieldState) {
        if (it.text.isEmpty()) {
            binding.etDescription.hint = it.hint
        } else {
            binding.etDescription.text = Editable.Factory.getInstance().newEditable(it.text)
            binding.etDescription.hint = ""
        }
    }

    private fun updateTitleNote(it: NoteTextFieldState) {
        if (it.text.isEmpty()) {
            binding.etTitle.hint = it.hint
        } else {
            binding.etTitle.text = Editable.Factory.getInstance().newEditable(it.text)
            binding.etTitle.hint = ""
        }
    }

    private fun updateColorNote(color: Int) {
        binding.layoutDetail.background = ColorDrawable(color)
    }

    private fun initUI() {
        initListener()
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

    private fun successState() {
        Toast.makeText(
            this@NoteDetailActivity,
            "NOTA GUARDADA",
            Toast.LENGTH_SHORT
        ).show()

        onBackPressedDispatcher.onBackPressed()
    }

    private fun errorState() {
        Toast.makeText(
            this@NoteDetailActivity,
            "HUBO UN ERROR",
            Toast.LENGTH_SHORT
        ).show()
    }

}