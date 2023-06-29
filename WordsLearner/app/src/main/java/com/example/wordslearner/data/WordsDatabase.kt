package com.example.wordslearner.data

import android.content.ClipData
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordsDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
    companion object {
        @Volatile
        private var Instance: WordsDatabase? = null

        fun getDatabase(context: Context): WordsDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, WordsDatabase::class.java, "word_database")
                    // Setting this option in your app's database builder means that Room
                    // permanently deletes all data from the tables in your database when it
                    // attempts to perform a migration with no defined migration path.
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}