package com.example.joeyweidman.hotseatbattleship

import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridLayout
import kotlinx.android.synthetic.main.activity_place_ships.*
import java.util.*

class PlaceShipsActivity : AppCompatActivity() {

    lateinit var grid: Array<Array<Cell>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_ships)

        if(MyApplication.currentPlayer == 1) {
            placeShipsTextView.text = "P1 Place Ships"
            grid = MyApplication.P1ShipGrid
        } else if (MyApplication.currentPlayer == 2) {
            placeShipsTextView.text = "P2 Place Ships"
            grid = MyApplication.P2ShipGrid
        }


        for(yPos in 0..9) {
            for(xPos in 0..9) {
                placeShipsGridLayout.addView(grid[xPos][yPos])
            }
        }

        placeShipsGridLayout.viewTreeObserver.addOnGlobalLayoutListener(
                {
                    val MARGIN = 5

                    var layoutWidth = placeShipsGridLayout.width
                    var layoutHeight = placeShipsGridLayout.height
                    val cellWidth = layoutWidth / MyApplication.GRID_SIZE
                    val cellHeight = layoutHeight / MyApplication.GRID_SIZE

                    for (yPos in 0..MyApplication.GRID_SIZE - 1) {
                        for (xPos in 0..MyApplication.GRID_SIZE - 1) {
                            val params = grid[xPos][yPos].layoutParams as GridLayout.LayoutParams
                            params.width = cellWidth - 2 * MARGIN
                            params.height = cellHeight - 2 * MARGIN
                            params.setMargins(MARGIN, MARGIN, MARGIN, MARGIN)
                            grid[xPos][yPos].layoutParams = params
                        }
                    }
                })

        autoButton.setOnClickListener {
            for(i in 0..9) {
                for(j in 0..9) {
                    grid[j][i].currentStatus = Status.EMPTY
                    grid[j][i].shipType = null
                }
            }
            placeRandomShips()
        }

        mainMenuButton.setOnClickListener {
            val intent: Intent = Intent(MyApplication.applicationContext(), MainActivity::class.java)
            startActivity(intent)
        }

        continueButton.setOnClickListener {

            if(MyApplication.currentPlayer == 1) {
                MyApplication.currentPlayer = 2
                MyApplication.saveGame(MyApplication.gameName)
                val intent: Intent = Intent(MyApplication.applicationContext(), PlaceShipsActivity::class.java)
                startActivity(intent)
            } else {
                MyApplication.currentPlayer = 1
                MyApplication.gameState = "In Progress"
                MyApplication.saveGame(MyApplication.gameName)
                val intent: Intent = Intent(MyApplication.applicationContext(), GameScreenActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun placeRandomShips() {
        for(ship in Ship.values()) {
            placeShip(ship)
        }
        MyApplication.saveGame(MyApplication.gameName)
    }

    fun placeShip(shipToPlace: Ship) {

        var potentialPlacement: Array<Point>
        start@while(true) {
            var random: Random = Random()
            val randomX: Int = random.nextInt(10)
            random = Random()
            val randomY: Int = random.nextInt(10)
            val currentPoint: Point = Point(randomX, randomY)
            val startingPoint: Point = Point(currentPoint)
            potentialPlacement = Array(shipToPlace.size, {Point()})
            potentialPlacement[0] = startingPoint
            val randomDirection: Direction = Direction.randomDirection()

            when(randomDirection) {
                Direction.NORTH -> {
                    for(i in 1..shipToPlace.size - 1) {
                        if(currentPoint.y - 1 < 0 || grid[currentPoint.x][currentPoint.y - 1].currentStatus != Status.EMPTY) {
                            continue@start
                        } else {
                            currentPoint.y--
                            val point: Point = Point(currentPoint)
                            potentialPlacement[i] = point
                            if(i == shipToPlace.size - 1)
                                break@start
                        }
                    }
                }
                Direction.SOUTH -> {
                    for(i in 1..shipToPlace.size - 1) {
                        if(currentPoint.y + 1 > 9 || grid[currentPoint.x][currentPoint.y + 1].currentStatus != Status.EMPTY) {
                            continue@start
                        } else {
                            currentPoint.y++
                            val point: Point = Point(currentPoint)
                            potentialPlacement[i] = point
                            if(i == shipToPlace.size - 1)
                                break@start
                        }
                    }
                }
                Direction.EAST -> {
                    for(i in 1..shipToPlace.size - 1) {
                        if(currentPoint.x + 1 > 9 || grid[currentPoint.x + 1][currentPoint.y].currentStatus != Status.EMPTY) {
                            continue@start
                        } else {
                            currentPoint.x++
                            val point: Point = Point(currentPoint)
                            potentialPlacement[i] = point
                            if(i == shipToPlace.size - 1)
                                break@start
                        }
                    }
                }
                Direction.WEST -> {
                    for(i in 1..shipToPlace.size - 1) {
                        if(currentPoint.x - 1 < 0 || grid[currentPoint.x - 1][currentPoint.y].currentStatus != Status.EMPTY) {
                            continue@start
                        } else {
                            currentPoint.x--
                            val point: Point = Point(currentPoint)
                            potentialPlacement[i] = point
                            if(i == shipToPlace.size - 1)
                                break@start
                        }
                    }
                }
            }
        }
        for(i in potentialPlacement) {
            grid[i.x][i.y].currentStatus = Status.SHIP
            grid[i.x][i.y].shipType = shipToPlace
        }
    }

    enum class Direction {
        NORTH, SOUTH, EAST, WEST;

        companion object {
            fun randomDirection(): Direction {
                var random: Random = Random()
                return values()[random.nextInt(values().size)]
            }
        }
    }
}
