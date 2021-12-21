package com.androidapp.movielist.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast

object Utils {
    fun showToast(context: Context?, message: String) {
        context?.let {
            Handler(Looper.getMainLooper()).post(Runnable {
                Toast.makeText(it, message, Toast.LENGTH_LONG).show()
            })
        }

    }
}