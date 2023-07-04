package com.example.wordslearner.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

//DAO(Data Access Object) Pozwala oddzielić bazę danych od reszty aplikacji. Abstrakcyjny intefrfejs ukrywa
@Dao
interface WordDao {
    @Query("SELECT * from word ORDER BY id ASC")
    fun getAllWords(): Flow<List<Word>> //Flow reaguje na zmiany dzięki czemu wystarczy pobrać dane raz a strumień danych zawsze będzie aktualny

    @Query("SELECT * from word WHERE id = :id")
    fun getWord(id: Int): Flow<Word>

    @Insert(onConflict = OnConflictStrategy.IGNORE) //w przypadku konfliktu nowy element jest ignorowany
    suspend fun insert(item: Word)

    @Update
    suspend fun update(item: Word)

    @Delete
    suspend fun delete(item: Word)
}