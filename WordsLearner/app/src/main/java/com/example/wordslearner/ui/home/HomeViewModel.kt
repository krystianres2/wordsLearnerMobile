package com.example.wordslearner.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordslearner.data.Word
import com.example.wordslearner.data.WordsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(wordsRepository: WordsRepository) : ViewModel() {

    /**
     * Holds home ui state. The list of items are retrieved from [WordsRepository] and mapped to
     * [HomeUiState]
     */
    val homeUiState: StateFlow<HomeUiState> =
        wordsRepository.getAllWordsStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )
    val wordsList: List<Word>
    get() = homeUiState.value.wordList
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}


/**
 * Ui State for HomeScreen
 */
data class HomeUiState(val wordList: List<Word> = listOf())