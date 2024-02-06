package com.example.notesapp.ui.notes.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.data.model.Note
import com.example.notesapp.databinding.ItemNoteBinding

class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemNoteBinding.bind(view)
    fun render(note: Note, onItemSelected: (Note) -> Unit) {
        with(binding) {
            tvTitle.text = note.title
            tvDescription.text = note.description
            parent.setOnClickListener { onItemSelected(note) }
        }
    }
}