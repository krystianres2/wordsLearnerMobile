package com.example.wordslearner.ui.word

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.wordslearner.data.Word
import com.example.wordslearner.data.WordsRepository

/**
 * View Model to validate and insert items in the Room database.
 */
class WordEntryViewModel(private val wordsRepository: WordsRepository) : ViewModel() {

    /**
     * Holds current item ui state
     */
    var wordUiState by mutableStateOf(WordUiState())
        private set

    /**
     * Updates the [wordUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
     */
    fun updateUiState(wordDetails: WordDetails) {
        wordUiState =
            WordUiState(wordDetails = wordDetails, isEntryValid = validateInput(wordDetails))
    }

    /**
     * Inserts an [Word] in the Room database
     */
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

/**
 * Represents Ui State for an Item.
 */
data class WordUiState(
    val wordDetails: WordDetails = WordDetails(),
    val isEntryValid: Boolean = false
)

data class WordDetails(
    val id: Int = 0,
    val wordPl: String = "",
    val wordEng: String = ""
)

/**
 * Extension function to convert [ItemUiState] to [Item]. If the value of [ItemUiState.price] is
 * not a valid [Double], then the price will be set to 0.0. Similarly if the value of
 * [ItemUiState] is not a valid [Int], then the quantity will be set to 0
 */
fun WordDetails.toWord(): Word = Word(
    id = id,
    wordPl = wordPl,
    wordEng = wordEng
)

/**
 * Extension function to convert [Item] to [ItemUiState]
 */
fun Word.toWordUiState(isEntryValid: Boolean = false): WordUiState = WordUiState(
    wordDetails = this.toWordDetails(),
    isEntryValid = isEntryValid
)

/**
 * Extension function to convert [Item] to [ItemDetails]
 */
fun Word.toWordDetails(): WordDetails = WordDetails(
    id = id,
    wordPl = wordPl,
    wordEng = wordEng
)