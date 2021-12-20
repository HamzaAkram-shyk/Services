package com.example.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MyService : Service() {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    private var limit = 0
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        limit = intent!!.getIntExtra("num", 0)
        scope.launch {
            while (limit!! > 0) {
                Log.d("Task", "$limit Task Completed.......")
                Thread.sleep(2000)
                limit--
            }
        }


        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        Log.d("Task", "Task Cancelled")
    }

    override fun onDestroy() {
        super.onDestroy()
        limit = 0
        job.cancel()
        Log.d("Task", "Service is Killed")
    }


}