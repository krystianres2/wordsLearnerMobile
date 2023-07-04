package com.example.wordslearner.ui.test

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordslearner.data.Word
import com.example.wordslearner.data.WordsRepository
import kotlinx.coroutines.flow.*
import kotlin.random.Random

class TestViewModel(wordsRepository: WordsRepository):ViewModel() {
    val testUiState: StateFlow<TestWords> = wordsRepository.getAllWordsStream()
        .map { TestWords(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = TestWords()
        )
    private val _uiState = MutableStateFlow(TestUiState())
    val uiState: StateFlow<TestUiState> = _uiState.asStateFlow()
    var userAnswer by mutableStateOf("")
        private set
    private var usedWords: MutableList<Word> = mutableListOf()
    private lateinit var currentWord: String
    private lateinit var correctAnswer: String

    init {

        try {
            correctAnswer="Phone"
            resetGame()
        }catch (e : NoSuchElementException){
            e.stackTrace
        }
    }

    private val wordsList: List<Word>
        get() = testUiState.value.wordList


    val listSize: Int
        get() = wordsList.size

    fun resetGame() {
        usedWords.clear()
        pickRandomObject()
        _uiState.value = TestUiState(
            currentWord = currentWord,
            currentCorrectAnswer = correctAnswer
        )
        if (_uiState.value.currentWord!=""){
            pom=1
        }
    }
    var pom = when(val x = _uiState.value.currentWord){
        "" -> 0
        else -> 1
    }
    fun updateUserAnswer(answeredWord: String) {
        userAnswer = answeredWord
    }

    fun checkUserAnswer() {
        if (userAnswer.equals(correctAnswer, ignoreCase = true)) {
            val updatedScore = _uiState.value.score.inc()
            updateGameState(updatedScore)
        } else {
            _uiState.update { currentState -> currentState.copy(isAnswerWrong = true) }
            val score = _uiState.value.score
            updateGameState(score)
        }
        updateUserAnswer("")
    }

    private fun updateGameState(updatedScore: Int) {
        if (usedWords.size == listSize) {
            _uiState.update { currentState -> currentState.copy(isAnswerWrong = false, score = updatedScore, isGameOver = true) }
        } else {
            pickRandomObject()
            _uiState.update { currentState -> currentState.copy(isAnswerWrong = false, currentWord = currentWord, currentCorrectAnswer = correctAnswer, currentWordCount = currentState.currentWordCount.inc(), score = updatedScore) }
        }
    }

    private fun pickRandomObject() {
        val Object = wordsList.random()
        val randomNumber = Random.nextInt(2)
        if(randomNumber==0) {
            currentWord = Object.wordPl
            correctAnswer = Object.wordEng
        }else{
            currentWord = Object.wordEng
            correctAnswer = Object.wordPl
        }
        if (usedWords.contains(Object)) {
            pickRandomObject()
        } else {
            usedWords.add(Object)
        }
    }


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}
data class TestWords(val wordList: List<Word> = listOf())