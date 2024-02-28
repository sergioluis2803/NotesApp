package com.example.notesapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
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

        val noteId = args.id
        noteDetailViewModel.getNoteDetail(noteId)
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
        val test = ContextCompat.getColor(this, color)
        binding.layoutDetail.setBackgroundColor(test)

        resetViewColors()
        when(color){
            R.color.redOrange -> { binding.view1.alpha = 1f }
            R.color.redPink -> { binding.view2.alpha = 1f }
            R.color.babyBlue -> { binding.view3.alpha = 1f }
            R.color.violet -> { binding.view4.alpha = 1f }
            R.color.lightGreen -> { binding.view5.alpha = 1f }
        }
    }

    private fun initUI() {
        initListener()
    }

    private fun initListener() {
        binding.btnSaveNote.setOnClickListener {
            val enteredTitle = binding.etTitle.text.toString()
            val enteredContent = binding.etDescription.text.toString()

            if (enteredTitle.isNotEmpty() && enteredContent.isNotEmpty()){
                noteDetailViewModel.onEvent(AddEditNoteEvent.EnteredTitle(enteredTitle))
                noteDetailViewModel.onEvent(AddEditNoteEvent.EnteredContent(enteredContent))
                noteDetailViewModel.onEvent(AddEditNoteEvent.SaveNote)
            }else{
                Toast.makeText(
                    this@NoteDetailActivity, getString(R.string.save_question), Toast.LENGTH_SHORT
                ).show()
            }
        }

        with(binding) {
            view1.setOnClickListener {
                noteDetailViewModel.onEvent(AddEditNoteEvent.ChangeColor(R.color.redOrange))
                resetViewColors()
                view1.alpha = 1f
            }
            view2.setOnClickListener {
                noteDetailViewModel.onEvent(AddEditNoteEvent.ChangeColor(R.color.redPink))
                resetViewColors()
                view2.alpha = 1f
            }
            view3.setOnClickListener {
                noteDetailViewModel.onEvent(AddEditNoteEvent.ChangeColor(R.color.babyBlue))
                resetViewColors()
                view3.alpha = 1f
            }
            view4.setOnClickListener {
                noteDetailViewModel.onEvent(AddEditNoteEvent.ChangeColor(R.color.violet))
                resetViewColors()
                view4.alpha = 1f
            }
            view5.setOnClickListener {
                noteDetailViewModel.onEvent(AddEditNoteEvent.ChangeColor(R.color.lightGreen))
                resetViewColors()
                view5.alpha = 1f
            }
        }
    }

    private fun resetViewColors(){
        with(binding){
            view1.alpha = 0.3f
            view2.alpha = 0.3f
            view3.alpha = 0.3f
            view4.alpha = 0.3f
            view5.alpha = 0.3f
        }
    }

    private fun successState() {
        Toast.makeText(
            this@NoteDetailActivity, getString(R.string.save_confirmation), Toast.LENGTH_SHORT
        ).show()

        onBackPressedDispatcher.onBackPressed()
    }

    private fun errorState() {
        Toast.makeText(
            this@NoteDetailActivity, getString(R.string.negation_save), Toast.LENGTH_SHORT
        ).show()
    }

}