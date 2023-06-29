package com.example.wordslearner.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val wordsRepository: WordsRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */
    override val wordsRepository: WordsRepository by lazy {
        OfflineWordsRepository(WordsDatabase.getDatabase(context).wordDao())
    }
}