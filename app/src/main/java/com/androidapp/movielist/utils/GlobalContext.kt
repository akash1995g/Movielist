package com.androidapp.movielist.utils

import android.app.Application
import android.content.Context

class GlobalContext : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: GlobalContext? = null

        fun applicationContext(): Context {
            if (instance == null) {
                instance = GlobalContext()
            }
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()

    }
}