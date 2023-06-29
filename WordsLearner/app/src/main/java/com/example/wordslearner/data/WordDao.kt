package com.example.wordslearner.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Query("SELECT * from word ORDER BY id ASC")
    fun getAllWords(): Flow<List<Word>>

    @Query("SELECT * from word WHERE id = :id")
    fun getWord(id: Int): Flow<Word>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Word)

    @Update
    suspend fun update(item: Word)

    @Delete
    suspend fun delete(item: Word)
}