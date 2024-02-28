package com.example.notesapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val title: String,
    val content: String,
    val color: Int,
    @PrimaryKey val id: Int? = null
)

class InvalidNoteException(message: String) : Exception(message)