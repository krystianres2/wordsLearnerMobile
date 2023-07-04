package com.example.wordslearner.data

import kotlinx.coroutines.flow.Flow

// udostępnia metody do manipulowania danymi wykorzytywane w aplikacji
interface WordsRepository {

    fun getAllWordsStream(): Flow<List<Word>>


    fun getWordStream(id: Int): Flow<Word?>


    suspend fun insertWord(word: Word)


    suspend fun deleteWord(word: Word)


    suspend fun updateWord(word: Word)
}