package com.example.notesapp.ui.notes.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.domain.model.Note

class NotesAdapter(
    private var notesList: List<Note> = emptyList(),
    private val onItemSelected: (Note) -> Unit,
    private val onItemDelete: (Note) -> Unit
) :
    RecyclerView.Adapter<NotesViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<Note>) {
        notesList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        )
    }

    override fun getItemCount() = notesList.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.render(notesList[position], onItemSelected, onItemDelete)
    }

}