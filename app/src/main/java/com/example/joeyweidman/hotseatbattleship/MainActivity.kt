package com.example.joeyweidman.hotseatbattleship

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myApp: MyApplication = MyApplication()

        MyApplication.P1ShipGrid = Array(10, {Array(10, {Cell(this, false)})})
        MyApplication.P1AttackGrid = Array(10, {Array(10, {Cell(this, true)})})
        MyApplication.P2ShipGrid = Array(10, {Array(10, {Cell(this, false)})})
        MyApplication.P2AttackGrid = Array(10, {Array(10, {Cell(this, true)})})

        //Button opens up the brush control activity
        newGameButton.setOnClickListener {
            MyApplication.writeObject("Test")
            val intent: Intent = Intent(MyApplication.applicationContext(), PlaceShipsActivity::class.java)
            startActivity(intent)
        }
    }
}
