package com.example.services

import android.app.IntentService
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

class NewService : IntentService("") {
    private var limit: Int = 0
    override fun onHandleIntent(intent: Intent?) {
        limit = intent!!.getIntExtra("num", 0)
        while (limit!! > 0) {
            Log.d("Task", "$limit Task Completed.......")
            Thread.sleep(3000)
            limit--
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        limit = 0
        Log.d("Task", "Service is Killed")
    }

}