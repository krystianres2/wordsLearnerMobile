package com.example.wordslearner.ui.test

data class TestUiState(
    val currentWord: String = "",
    val currentCorrectAnswer: String = "",
    val currentWordCount: Int = 1,
    val score: Int = 0,
    val isAnswerWrong: Boolean = false,
    val isGameOver: Boolean = false
)
