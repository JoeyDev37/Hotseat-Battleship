package com.example.joeyweidman.hotseatbattleship

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_text.*

class TextActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)

        val status: String = intent.getStringExtra("STATUS")
        statusText.text = status
        if(statusText.text == "HIT")
            statusText.setBackgroundColor(Color.GREEN)
        if(statusText.text == "MISS")
            statusText.setBackgroundColor(Color.RED)

        nextPlayerButton.setOnClickListener {
            val intent: Intent = Intent(applicationContext, GameScreenActivity::class.java)
            startActivity(intent)
        }
    }
}
