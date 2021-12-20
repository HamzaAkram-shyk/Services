package com.example.forgroundservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.services.R

class ServiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)

        findViewById<Button>(R.id.start).setOnClickListener {
            ForegroundService.startService(this, "Hello there...")
        }

        findViewById<Button>(R.id.stop).setOnClickListener {
            ForegroundService.startService(this, "Close")
        }

    }
}