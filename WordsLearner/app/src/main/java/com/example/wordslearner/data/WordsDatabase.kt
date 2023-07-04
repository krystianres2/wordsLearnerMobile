package com.example.wordslearner.data

import android.content.ClipData
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Word::class], version = 1, exportSchema = false)// exportSchema określa czy trzymać backup zmian schematu bazy
abstract class WordsDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
    companion object {
        @Volatile //zapobiega korzystaaniu z pamięci podręcznej
        private var Instance: WordsDatabase? = null

        fun getDatabase(context: Context): WordsDatabase { //zwraca instancję bazy danych, jeśli nie istnieje to ją tworzy
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, WordsDatabase::class.java, "word_database")

                    .fallbackToDestructiveMigration() //w przypadku zmiany schematu bazy, dane są niszczone
                    .build()
                    .also { Instance = it }
            }
        }
    }
}