package com.example.wordslearner.data

import kotlinx.coroutines.flow.Flow

interface WordsRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllWordsStream(): Flow<List<Word>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getWordStream(id: Int): Flow<Word?>

    /**
     * Insert item in the data source
     */
    suspend fun insertWord(word: Word)

    /**
     * Delete item from the data source
     */
    suspend fun deleteWord(word: Word)

    /**
     * Update item in the data source
     */
    suspend fun updateWord(word: Word)
}