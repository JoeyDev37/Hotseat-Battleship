package com.example.joeyweidman.hotseatbattleship

import android.content.Intent
import android.graphics.Path
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_place_ships.*
import java.util.*

class PlaceShipsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_ships)

        autoButton.setOnClickListener {
            placeRandomShips()
        }

        continueButton.setOnClickListener {
            val intent: Intent = Intent(applicationContext, GameScreenActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }
    }

    fun placeRandomShips() {
        for(ship in Ship.values()) {
            placeShip(ship)
        }
    }

    fun placeShip(shipToPlace: Ship) {
        var shipPlaced: Boolean = false
        while(!shipPlaced) {
            var random: Random = Random()
            var randomX: Int = random.nextInt(10)
            var randomY: Int = random.nextInt(10)
            GameInfo.statusGridHistoryP1[randomX][randomY] = Status.SHIP
            var randomDirection: Direction = random.nextInt(4) as Direction

            //for(i in 0..shipToPlace.size) {
            //    if(randomDirection == Direction.NORTH)
            //}
        }
    }

    enum class Direction(value: Int) {
        NORTH(0), SOUTH(1), EAST(2), WEST(3)
    }
}
