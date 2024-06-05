package com.smokey.practicafct

import android.app.Application
import android.content.Context

class ContextApplication: Application(){
     override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
    companion object {
        lateinit var context : Context
    }
}