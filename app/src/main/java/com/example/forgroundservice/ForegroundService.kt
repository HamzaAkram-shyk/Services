package com.example.forgroundservice
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat.stopForeground
import androidx.core.content.ContextCompat
import com.example.services.R
import kotlinx.coroutines.*

class ForegroundService : Service() {
    private val CHANNEL_ID = "ForegroundService Kotlin"
    var notification: Notification? = null

    var job: Job? = null

    companion object {
        fun startService(context: Context, message: String) {
            val startIntent = Intent(context, ForegroundService::class.java)
            startIntent.putExtra("inputExtra", message)
            ContextCompat.startForegroundService(context, startIntent)
        }

        fun stopService(context: Context) {
            val stopIntent = Intent(context, ForegroundService::class.java)
            context.stopService(stopIntent)
        }

    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val input = intent?.getStringExtra("inputExtra")
        createNotificationChannel()
        if (input.equals("Close")) {
            stopSelf()
            stopForeground(true)
            Log.d("Task", "Called Stop Foreground")
        } else {
            val notificationIntent = Intent(this, ServiceActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                this,
                0, notificationIntent, 0
            )
            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Service is Running.......")
                .setContentText(input)
                .setSmallIcon(R.drawable.icon)
                .setContentIntent(pendingIntent)
            builder.color = ContextCompat.getColor(this, R.color.teal_200)
            notification = builder.build()
            startForeground(1, notification)
            job = CoroutineScope(Dispatchers.IO).launch {
                performTask()


            }
        }


        return START_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_MIN
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Task", "JOb = $job")
        job?.cancel()
        job = null
        //  job=null
        Log.d("Task", "Service is Killed")
    }

    private suspend fun performTask() {
        var limit = 25
        while (limit > 0) {
            Log.d("Task", "$limit Task Completed.......")
            Thread.sleep(3000)
            limit--
        }
        stopForeground(true)
        stopSelf()
    }


}