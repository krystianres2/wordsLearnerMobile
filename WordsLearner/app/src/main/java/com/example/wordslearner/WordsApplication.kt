package com.example.wordslearner

import android.app.Application
import com.example.wordslearner.data.AppContainer
import com.example.wordslearner.data.AppDataContainer

class WordsApplication : Application(){
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}