package com.example.wordslearner.ui.word

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordslearner.data.WordsRepository
import kotlinx.coroutines.flow.*

class WordDetailsViewModel(savedStateHandle: SavedStateHandle,
private val wordsRepository: WordsRepository,) :ViewModel(){

    private val wordId: Int = checkNotNull(savedStateHandle[WordDetailsDestination.wordIdArg])


    val uiState: StateFlow<WordDetailsUiState> =
        wordsRepository.getWordStream(wordId)
            .filterNotNull()
            .map {
                WordDetailsUiState(wordDetails = it.toWordDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = WordDetailsUiState()
            )


    suspend fun deleteWord() {
        wordsRepository.deleteWord(uiState.value.wordDetails.toWord())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

data class WordDetailsUiState(
    val wordDetails: WordDetails = WordDetails()
)