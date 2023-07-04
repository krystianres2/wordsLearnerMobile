package com.example.wordslearner.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.wordslearner.WordsApplication
import com.example.wordslearner.ui.home.HomeViewModel
import com.example.wordslearner.ui.test.TestViewModel
import com.example.wordslearner.ui.word.WordDetailsViewModel
import com.example.wordslearner.ui.word.WordEditViewModel
import com.example.wordslearner.ui.word.WordEntryViewModel

//inicjalizacja ViewModeli/tworzenie ich instancji
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            WordEditViewModel(
                this.createSavedStateHandle(),// umożliwia przechowywanie i przywracanie danych pomimo zmian w cyklu
                wordsApplication().container.wordsRepository// dostarcza repozytorium do ViewModeli
            )
        }

        initializer {
            WordEntryViewModel(wordsApplication().container.wordsRepository)
        }


        initializer {
            WordDetailsViewModel(
                this.createSavedStateHandle(),
                wordsApplication().container.wordsRepository
            )
        }


        initializer {
            HomeViewModel(wordsApplication().container.wordsRepository)
        }

        initializer {
            TestViewModel(wordsApplication().container.wordsRepository)
        }
    }
}


fun CreationExtras.wordsApplication(): WordsApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WordsApplication)