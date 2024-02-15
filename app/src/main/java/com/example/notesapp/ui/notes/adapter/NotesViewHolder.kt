package com.example.notesapp.ui.notes.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.ItemNoteBinding
import com.example.notesapp.domain.model.Note

class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemNoteBinding.bind(view)
    fun render(note: Note, onItemSelected: (Note) -> Unit) {
        with(binding) {
            tvTitle.text = note.title
            tvDescription.text = note.content
            parent.setOnClickListener { onItemSelected(note) }
        }
    }
}