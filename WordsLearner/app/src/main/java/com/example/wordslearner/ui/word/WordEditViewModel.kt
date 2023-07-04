package com.example.wordslearner.ui.word

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordslearner.data.WordsRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class WordEditViewModel(savedStateHandle: SavedStateHandle,
private val wordsRepository: WordsRepository
):ViewModel() {


    var wordUiState by mutableStateOf(WordUiState())
        private set

    private val itemId: Int = checkNotNull(savedStateHandle[WordEditDestination.wordIdArg])

    init {
        viewModelScope.launch {
            wordUiState = wordsRepository.getWordStream(itemId)
                .filterNotNull()
                .first()
                .toWordUiState(true)
        }
    }


    suspend fun updateItem() {
        if (validateInput(wordUiState.wordDetails)) {
            wordsRepository.updateWord(wordUiState.wordDetails.toWord())
        }
    }


    fun updateUiState(wordDetails: WordDetails) {
        wordUiState =
            WordUiState(wordDetails = wordDetails, isEntryValid = validateInput(wordDetails))
    }

    private fun validateInput(uiState: WordDetails = wordUiState.wordDetails): Boolean {
        return with(uiState) {
            wordPl.isNotBlank() && wordEng.isNotBlank()
        }
    }
}