package com.example.wordslearner.ui.word

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.wordslearner.data.Word
import com.example.wordslearner.data.WordsRepository


class WordEntryViewModel(private val wordsRepository: WordsRepository) : ViewModel() {


    var wordUiState by mutableStateOf(WordUiState())
        private set


    fun updateUiState(wordDetails: WordDetails) {
        wordUiState =
            WordUiState(wordDetails = wordDetails, isEntryValid = validateInput(wordDetails))
    }

    //dodawnie zeedytyowanego obiektu do bazy
    suspend fun saveWord() {
        if (validateInput()) {
            wordsRepository.insertWord(wordUiState.wordDetails.toWord())
        }
    }

    private fun validateInput(uiState: WordDetails = wordUiState.wordDetails): Boolean {
        return with(uiState) {
            wordPl.isNotBlank() && wordEng.isNotBlank()
        }
    }
}


data class WordUiState(
    val wordDetails: WordDetails = WordDetails(),
    val isEntryValid: Boolean = false
)

data class WordDetails(
    val id: Int = 0,
    val wordPl: String = "",
    val wordEng: String = ""
)

//konwersja z UiState na Entity
fun WordDetails.toWord(): Word = Word(
    id = id,
    wordPl = wordPl,
    wordEng = wordEng
)

//konwersja obiektu typu Entity na typ UiState
fun Word.toWordUiState(isEntryValid: Boolean = false): WordUiState = WordUiState(
    wordDetails = this.toWordDetails(),
    isEntryValid = isEntryValid
)

//konwersja obiektu entity na obiekt wordDetails
fun Word.toWordDetails(): WordDetails = WordDetails(
    id = id,
    wordPl = wordPl,
    wordEng = wordEng
)