package com.example.blockgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get reference to button
        val btn_click_me = findViewById(R.id.start_button) as Button
// set on-click listener
        btn_click_me.setOnClickListener {
            Toast.makeText(this@MainActivity, "Zaczynamy!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, GameScreenActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}