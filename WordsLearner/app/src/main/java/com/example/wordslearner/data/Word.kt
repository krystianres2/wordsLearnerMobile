package com.example.wordslearner.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val wordPl: String,
    val wordEng: String
)
