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
        if(statusText.text == "SUNK DESTROYER" || statusText.text == "SUNK SUBMARINE" || statusText.text == "SUNK CRUISER" || statusText.text == "SUNK BATTLESHIP" || statusText.text == "SUNK CARRIER")
            statusText.setBackgroundColor(Color.rgb(244, 140, 66))
        if(statusText.text == "P1 VICTORY" || statusText.text == "P2 VICTORY")
            statusText.setBackgroundColor(Color.BLUE)

        nextPlayerButton.setOnClickListener {
            MyApplication.saveGame(MyApplication.gameName)
            val intent: Intent = Intent(applicationContext, GameScreenActivity::class.java)
            startActivity(intent)
        }

        mainMenuButton1.setOnClickListener {
            val intent: Intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
