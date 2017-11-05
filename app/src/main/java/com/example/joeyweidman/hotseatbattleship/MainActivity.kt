package com.example.joeyweidman.hotseatbattleship

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GameInfo.P1ShipGrid = Array(10, {Array(10, {Cell(this, false)})})
        GameInfo.P1AttackGrid = Array(10, {Array(10, {Cell(this, true)})})
        GameInfo.P2ShipGrid = Array(10, {Array(10, {Cell(this, false)})})
        GameInfo.P2AttackGrid = Array(10, {Array(10, {Cell(this, true)})})

        //Button opens up the brush control activity
        newGameButton.setOnClickListener {
            val intent: Intent = Intent(applicationContext, PlaceShipsActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }
    }
}
