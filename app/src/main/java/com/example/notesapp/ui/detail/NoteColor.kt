package com.example.notesapp.ui.detail

import com.example.notesapp.R

data class NoteColor(
    val redOrange: Int = R.color.redOrange,
    val redPink: Int = R.color.redPink,
    val babyBlue: Int = R.color.babyBlue,
    val violet: Int = R.color.violet,
    val lightGreen: Int = R.color.lightGreen
){
    fun getColorRandom(): Int{
        val colors = listOf(redOrange, redPink, babyBlue, violet, lightGreen)
        return colors.random()
    }
}