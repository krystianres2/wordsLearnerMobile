package com.example.wordslearner.data

import android.content.Context


interface AppContainer {
    val wordsRepository: WordsRepository
}

//implementacja AppContainer która dostarcza instancję OfflineWordsRepository
class AppDataContainer(private val context: Context) : AppContainer {
    // twworzy instację instancji bazy danych
    override val wordsRepository: WordsRepository by lazy {
        OfflineWordsRepository(WordsDatabase.getDatabase(context).wordDao())
    }
}