package com.example.services

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button).setOnClickListener {
            Intent(this, MyService::class.java).apply {
                putExtra("num", 50)
                startService(this)
            }
        }
        findViewById<Button>(R.id.stop).setOnClickListener {
            Intent(this, MyService::class.java).also {
                stopService(it)
            }
        }


    }
}