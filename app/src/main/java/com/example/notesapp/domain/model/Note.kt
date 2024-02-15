package com.example.notesapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notesapp.R

@Entity
data class Note(
    val title: String,
    val content: String,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors =
            listOf(R.color.redOrange, R.color.lightGreen, R.color.violet, R.color.babyBlue, R.color.redPink)
    }
}


class InvalidNoteException(message: String) : Exception(message)