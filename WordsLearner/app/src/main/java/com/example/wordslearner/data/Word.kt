package com.example.wordslearner.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//Definiowana jest tabela (nazwy kolunm i ich typy), każdą z kolunm stanowi Entity field

@Entity
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val wordPl: String,
    val wordEng: String
)
