package com.example.readerclient

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class ReaderClientApplication: Application() {
     var TOKEN = ""
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        TOKEN = TOKEN
    }
}