package com.example.wordslearner

import android.app.Application
import com.example.wordslearner.data.AppContainer
import com.example.wordslearner.data.AppDataContainer

class WordsApplication : Application(){

    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}